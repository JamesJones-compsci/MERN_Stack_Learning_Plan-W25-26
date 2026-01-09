import { useState } from "react";
import api from '../api/axios';
import { useAuth } from "../auth/AuthContext";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const res = await api.post("/auth/login", { email, password });
    login(res.data.token);
    navigate("/dashboard");
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <input 
          value={email} 
          onChange={(e) => setEmail(e.target.value)} 
          placeholder="Email" 
        />
        <input 
          type="password" 
          value={password} 
          onChange={(e) => setPassword(e.target.value)} 
          placeholder="Password" 
        />
        <button type="submit">Login</button>
      </form>

      {/* Signup link for new users */}
      <p>
        Don’t have an account? <a href="/signup">Sign up here</a>
      </p>
    </div>
  );
}
 