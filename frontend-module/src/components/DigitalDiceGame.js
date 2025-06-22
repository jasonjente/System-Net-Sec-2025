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
        const clientRandomString = generateRandomString(); // Generate the random string based on time

        try {
            const token = localStorage.getItem('jwtToken');
            if (!token) {
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
                const hashResult = await response.text();
                localStorage.setItem('gameHash', hashResult);  // Store the hash in localStorage
                setMessage(`Game started! Hash result: ${hashResult}`);
                console.log('Game started:', hashResult);

                // Navigate to the GuessDice page once the game is successfully started
                navigate('/guess-dice');
            } else {
                setMessage('Failed to start the game. Please try again.');
            }
        } catch (error) {
            setMessage('Error connecting to the server.');
            console.error(error);
        }
    };

    // Handle the log off button click
    const handleLogOff = () => {
        // Remove the JWT token from localStorage
        localStorage.removeItem('jwtToken');
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