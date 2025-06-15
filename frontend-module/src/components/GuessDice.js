// src/components/GuessDice.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function GuessDice() {
    const navigate = useNavigate();
    const [guess, setGuess] = useState('');
    const [message, setMessage] = useState('');
    const [gameResult, setGameResult] = useState(null);

    const handleGuessSubmit = async (e) => {
        e.preventDefault();

        if (guess < 1 || guess > 6) {
            setMessage('Please enter a number between 1 and 6.');
            return;
        }

        try {
            const token = localStorage.getItem('jwtToken');
            if (!token) {
                setMessage('Please log in first.');
                return;
            }

            // Send the guess to the backend
            const response = await fetch(`/api/game/guess/${guess}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`, // Attach the JWT token for authentication
                },
            });

            if (response.ok) {
                const result = await response.json();
                setGameResult(result);  // Assuming the response is the result of the game
                setMessage('Your guess was submitted successfully!');
            } else {
                setMessage('Failed to submit your guess. Please try again.');
            }
        } catch (error) {
            setMessage('Error connecting to the server.');
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
                        />
                    </label>
                </div>
                <button type="submit" style={{ marginTop: 10 }}>Submit Guess</button>
            </form>
            {message && <p>{message}</p>}
            {gameResult && (
                <div>
                    <h3>Game Result</h3>
                    <p>{gameResult.message}</p> {/* Assuming the response has a message */}
                </div>
            )}
        </div>
    );
}

export default GuessDice;
