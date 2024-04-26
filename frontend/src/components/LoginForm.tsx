import React, { ChangeEvent, createContext, FormEvent, useContext, useEffect, useState } from 'react';
import { background, Button } from '@chakra-ui/react';
import { useNavigate } from 'react-router';


type AuthContextType = {
  authHeader: string | null;
  setAuthHeader: React.Dispatch<React.SetStateAction<string | null>>;
};

const AuthContext = createContext<AuthContextType>({
  authHeader: null,
  setAuthHeader: () => {},
});

const useAuth = () => useContext(AuthContext);

export default function LoginForm() {
  
  const [formData, setFormData] = useState({ username: '', password: '' });
  const {setAuthHeader} = useAuth();

  const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setFormData((prevFormData) => ({ ...prevFormData, [name]: value }));
  };

  const nav = useNavigate();
  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    //alert(`Name: ${formData.username}, pass: ${formData.password}}`);
    const base64Credentials = btoa(formData.username + ':' + formData.password);
    fetch('http://localhost:8080/api/user', {
      method: 'POST',
      body: JSON.stringify({
        username: formData.username,
        password: formData.password,
      }),
      headers: {
        Authorization: 'Basic ' + base64Credentials,
        'Content-Type': 'application/json',
      },
    })
      .then((response) => {
        if (!response.ok) {
          alert("Username or password is incorrect")
          throw new Error('Username or password is incorrect');
        }
        return response.json();
      })
      .then((data) => {
        localStorage.setItem('authHeader', base64Credentials);
        setAuthHeader('Basic ' + base64Credentials);
        localStorage.setItem('role', data.role);
        localStorage.setItem('id', data.id);
        alert("Succesfull login")
        console.log(localStorage.getItem('id'));
        nav('/main');
      })
      .catch((error) => {
        console.error('There was a problem with the fetch operation:', error);
      });
  };

  const inputStyle = {
    margin: '0px',
    padding: '10px',
    borderRadius: '5px',
    boxShadow: 'rgba(60, 64, 67, 0.15) 0px 1px 3px 1px'
    
  }

  const labelStyle = { 
    paddingTop: '2vw',
    fontSize: '20px',
    textTransform: 'uppercase',
    textAlign: ' left',
    color: '#001650',
  }

  const buttonStyle = {
    margin: '2vw',
    padding: '1.5vw 4vw',
    borderRadius: '5px',
    backgroundColor: '#001650',
    textTransform: 'uppercase',
    boxShadow: 'rgba(60, 64, 67, 0.15) 0px 1px 3px 1px',
    color: 'white'
  }

  return (
    <div>
      <div className="login-status">
      </div>
      <form onSubmit={handleSubmit}>
        <label style ={labelStyle}>Username</label>
        <input style={inputStyle} name="username" value={formData.username} onChange={handleChange} type="text" required placeholder="Enter your login"/>
        <label style ={labelStyle}>Password</label>
        <input style={inputStyle} name="password" value={formData.password} onChange={handleChange} type="password" required placeholder="Enter your password"/>
        <Button style={buttonStyle} type="submit">Log in</Button>
      </form>
    </div>
  );
}

export { useAuth };