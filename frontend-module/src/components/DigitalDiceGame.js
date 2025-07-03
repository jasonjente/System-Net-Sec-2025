import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function DigitalDiceGame() {
    const navigate = useNavigate();
    const [message, setMessage] = useState('');

    // Function to generate a random string based on the current timestamp
    const generateRandomString = () => {
        const timestamp = Date.now(); // Current time in milliseconds
        const randomPart = Math.random().toString(36).substring(2, 15); // Random alphanumeric string
        return `${timestamp}-${randomPart}`; // Combine timestamp with the random part
    };

    // Handle the start button click
    const handleStart = async () => {
        console.log("Game started, generating random string");
        const clientRandomString = generateRandomString(); // Generate the random string based on time
        console.log("Random string generated. String is: ", clientRandomString);

        try {
            const token = localStorage.getItem('jwtToken');
            if (!token) {
                console.log("You are not currently logged in")
                setMessage('Please log in first.');
                return;
            }

            // Send the request to the backend API with the random string as a path parameter
            const response = await fetch(`/api/game/${clientRandomString}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`, // Attach the JWT token for authentication
                },
            });

            if (response.ok) {
                console.log("Successfully sent string to server. Response is: ", response.status);
                const hashResult = await response.text();
                console.log("Successfully got hash from server: Hash is: ", hashResult)
                console.log("Storing hash to localStorage...")
                localStorage.setItem('gameHash', hashResult);  // Store the hash in localStorage
                console.log("Hash is now stored to localStorage")
                setMessage(`Game started! Hash result: ${hashResult}`);
                console.log('Game started:', hashResult);

                // Navigate to the GuessDice page once the game is successfully started
                navigate('/guess-dice');
            } else {
                console.error("Failed to start the game. Please try again")
                setMessage('Failed to start the game. Please try again.');
            }
        } catch (error) {
            console.error("Error connecting to the server")
            setMessage('Error connecting to the server.');
            console.error(error);
        }
    };

    // Handle the log off button click
    const handleLogOff = () => {
        // Remove the JWT token from localStorage
        localStorage.removeItem('jwtToken');
        console.log("You have been logged off")
        setMessage('You have been logged off.');

        // Redirect to the login page
        navigate('/login');
    };

    return (
        <div style={{ textAlign: 'center', marginTop: 50 }}>
            <h2>Digital Dice Game</h2>
            <button onClick={handleStart} style={{ marginTop: 20 }}>
                Start
            </button>

            {/* Log off button */}
            <button onClick={handleLogOff} style={{ marginTop: 20, marginLeft: 10 }}>
                Log Off
            </button>

            {message && <p>{message}</p>}

            {/* Display the stored hash from localStorage 
            {localStorage.getItem('gameHash') && (
                <p>Stored Hash: {localStorage.getItem('gameHash')}</p>
            )}*/}
        </div>
    );
}

export default DigitalDiceGame;