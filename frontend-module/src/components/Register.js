import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';

function Register() {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [message, setMessage] = useState('');

    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log("Registration procedure started...");

        try {
            const response = await fetch('/api/auth/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    firstName,
                    lastName,
                    username,
                    password,
                    email,
                }),
            });

            if (response.ok) {
                console.log("Registration succssful! Redirecting to login...")
                setMessage('Registration successful! Redirecting to login...');
                setTimeout(() => navigate('/login'), 1500);
            } else {
                const data = await response.json();
                console.error("Registration failed");
                setMessage(data.details || 'Registration failed.');
            }
        } catch (error) {
            console.error("Server error");
            setMessage('Server error.');
        }
    };

    return (
        <div style={{ maxWidth: '300px', margin: 'auto', textAlign: 'center' }}>
            <h2>Register</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>First Name:</label><br />
                    <input
                        type="text"
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Last Name:</label><br />
                    <input
                        type="text"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Username:</label><br />
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Password:</label><br />
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Email:</label><br />
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" style={{ marginTop: 10 }}>Register</button>
            </form>
            <p>{message}</p>

            <Link to="/" style={{ display: 'inline-block', marginTop: 20 }}>
                Back to Home
            </Link>
        </div>
    );
}

export default Register;
