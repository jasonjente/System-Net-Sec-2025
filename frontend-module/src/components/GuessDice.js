import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function GuessDice() {
    const navigate = useNavigate();
    const [guess, setGuess] = useState('');
    const [message, setMessage] = useState('');
    const [gameResult, setGameResult] = useState(null);
    const [isHashMatch, setIsHashMatch] = useState(null);
    const [clientHash, setClientHash] = useState('');
    const [serverHash, setServerHash] = useState('');
    const [storedHash, setStoredHash] = useState('');
    const [showDetails, setShowDetails] = useState(false);
    const [isSubmitted, setIsSubmitted] = useState(false); // Track if the guess has been submitted

    // Handle the guess form submission
    const handleGuessSubmit = async (e) => {
        e.preventDefault();

        if (guess < 1 || guess > 6) {
            console.log("Number entered not between 1 and 6.");
            setMessage('Please enter a number between 1 and 6.');
            return;
        }

        try {
            const token = localStorage.getItem('jwtToken');
            if (!token) {
                console.log("You have to log in first...");
                setMessage('Please log in first.');
                return;
            }

            // Send the guess to the backend
            console.log("Sending guess to server. Guess is: ", guess)
            const response = await fetch(`/api/game/guess/${guess}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                },
            });

            if (response.ok) {
                console.log("Guess submitted successfully! Retrieving server's response");
                const result = await response.json();
                console.log("Server's Guess:", result.serverGuess);
                console.log("Server's stored ClientRandomString:", result.randomClientString);
                console.log("Server's stored ServerRandomString:", result.randomServerString);
                setGameResult(result);  // Assuming the response is the result of the game
                setMessage('Your guess was submitted successfully!');
                setIsSubmitted(true); // Mark the guess as submitted

                // Retrieve the stored hash from localStorage (gameHash from DigitalDiceGame)
                console.log("Retrieving stored hash")
                const storedHash = localStorage.getItem('gameHash');
                if (!storedHash) {
                    console.error("Error, game hash not found");
                    setMessage('Error: Game hash not found. Please start a new game.');
                    return;
                }
                console.log("Successfully got hash: ", storedHash)
                setStoredHash(storedHash); // Set the stored hash to be displayed

                console.log("Calculating Client's hash...");
                // Calculate the hash on the client side using server's response
                const calculatedClientHash = await calculateHash(
                    result.serverGuess,
                    result.randomClientString,
                    result.randomServerString
                );
                console.log("Calculated Hash is: ", calculatedClientHash);

                // Set client-side and server hashes for display
                setClientHash(calculatedClientHash);
                setServerHash(result.serverHash);

                console.log("Comparing the calculated hash and stored hash...");
                // Compare the calculated hash with the stored hash
                if (calculatedClientHash === storedHash) {
                    setIsHashMatch(true);
                    console.log("Hashes are the same, game is fair");
                    setMessage('The result is fair!');
                } else {
                    setIsHashMatch(false);
                    console.log("Hashes are not the same, game not fair");
                    setMessage('Result not accepted: Not fair.');
                }
            } else {
                setMessage('Last game was finished, please press play again.');
            }
        } catch (error) {
            console.log("Error connecting to server")
            setMessage('Error connecting to the server.');
            console.error(error);
        }
    };

    // Calculate SHA-256 hash based on server's choice, client and server random strings
    const calculateHash = async (serverGuess, clientRandomString, serverRandomString) => {
        // Concatenate the server's choice, client random string, and server random string
        const data = serverGuess + clientRandomString + serverRandomString;

        const encoder = new TextEncoder();
        const dataBuffer = encoder.encode(data); // Convert to byte array
        const hashBuffer = await crypto.subtle.digest('SHA-256', dataBuffer); // Calculate hash
        const hashArray = Array.from(new Uint8Array(hashBuffer)); // Convert to array
        const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join(''); // Convert to hex string
        return hashHex;
    };

    // Handle log off button click
    const handleLogOff = () => {
        // Remove the JWT token from localStorage
        localStorage.removeItem('jwtToken');
        console.log("User logged off")
        setMessage('You have been logged off.');

        // Redirect to the login page
        navigate('/login');
    };

    // Play Again button handler
    const handlePlayAgain = async () => {
        setGuess('');
        setMessage('');
        setGameResult(null);
        setIsHashMatch(null);
        setClientHash('');
        setServerHash('');
        setStoredHash('');
        setIsSubmitted(false); // Reset submission state

        // Function to generate a random string based on the current timestamp
        const generateRandomString = () => {
            const timestamp = Date.now(); // Current time in milliseconds
            const randomPart = Math.random().toString(36).substring(2, 15); // Random alphanumeric string
            return `${timestamp}-${randomPart}`; // Combine timestamp with the random part
        };

        const clientRandomString = generateRandomString(); // Generate the random string based on time

        try {
            const token = localStorage.getItem('jwtToken');
            if (!token) {
                console.log("You need to login again");
                setMessage('Please log in again.');
                return;
            }

            console.log("New game started");
            // Call startGame with the new client string
            const response = await fetch(`/api/game/${clientRandomString}`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            });

            if (response.ok) {
                console.log("Successfully sent string to server. Response is: ", response.status);
                const hash = await response.text();
                console.log("Successfully got hash from server: Hash is: ", hash)
                console.log("Storing hash to localStorage...")
                localStorage.setItem('gameHash', hash);
                console.log("Hash is now stored to localStorage");
                setMessage('New game started. Make your guess!');
                console.log('Game started:', hash);
            } else {
                console.error("Failed to start a new game")
                setMessage('Failed to start a new game.');
            }
        } catch (error) {
            console.error("Error starting new games")
            setMessage('Error starting new game.');
            console.error(error);
        }
    };

    return (
        <div style={{ textAlign: 'center', marginTop: 50 }}>
            <h2>Make Your Dice Guess</h2>
            <form onSubmit={handleGuessSubmit}>
                <div>
                    <label>
                        Guess a number between 1 and 6:
                        <input
                            type="number"
                            value={guess}
                            onChange={(e) => setGuess(e.target.value)}
                            min="1"
                            max="6"
                            required
                            disabled={isSubmitted} // Disable button after submission
                        />
                    </label>
                </div>
                {!isSubmitted && ( // Only show the submit button if the guess hasn't been submitted
                    <button type="submit" style={{ marginTop: 10 }}>
                        Submit Guess
                    </button>
                )}
            </form>

            {/* Display messages */}
            {message && <p>{message}</p>}

            {/* Display the game result */}
            {gameResult && (
                <div>
                    <h3>Game Result</h3>
                    <p>Server Guess: {gameResult.serverGuess}</p>
                    <p style={{ color: gameResult.win ? 'green' : 'red' }}>
                      Win: {gameResult.win ? 'Yes' : 'No'}
                    </p>
                </div>
            )}

            {/* Expandable hash details */}
            {(clientHash || serverHash || storedHash) && (
                <div style={{ marginTop: 20 }}>
                    <button onClick={() => setShowDetails(prev => !prev)}>
                        {showDetails ? 'Hide Details' : 'Show Details'}
                    </button>
                    {showDetails && (
                        <div>
                            <section>
                                <h4>Client&Server random strings:</h4>
                                <p>Client Random String: {gameResult.randomClientString}</p>
                                <p>Server Random String: {gameResult.randomServerString}</p>
                            </section>
                            <section>
                                <h4>Hashes Comparison</h4>
                                <p><strong>Client Calculated Hash:</strong> {clientHash}</p>
                                <p><strong>Server Hash:</strong> {serverHash}</p>
                                <p><strong>Stored Hash (from DigitalDiceGame):</strong> {storedHash}</p>
                            </section>
                        </div>
                    )}
                </div>
            )}

            {/* Log off button */}
            <button onClick={handleLogOff} style={{ marginTop: 20 }}>
                Log Off
            </button>

            {/* Play Again button */}
            {isHashMatch !== null && (
                <button onClick={handlePlayAgain} style={{ marginTop: 20 }}>
                    Play Again
                </button>
            )}

            {/* Display the hash match result */}
            {isHashMatch !== null && (
                <p>{isHashMatch ? 'The result is fair!' : 'Result not accepted: Not fair.'}</p>
            )}
        </div>
    );
}

export default GuessDice;