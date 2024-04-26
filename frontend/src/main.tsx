import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './style/index.css'
import Apartament from './Apartament.tsx'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Profile from './Profile.tsx'
import { ChakraProvider } from '@chakra-ui/react'
import Header from './components/Header.tsx'
import Login from './Login.tsx'

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <ChakraProvider>
      <BrowserRouter>
        <div>
          <Header />
          <Routes>
            <Route path="/" element={<Login />} />
            <Route path="/apartment/:identifier" element={<Apartament />} />
            <Route path="profile" element={<Profile />} />
            <Route path="/main" element={<App />} />
          </Routes>
        </div>
      </BrowserRouter>
    </ChakraProvider>
  </React.StrictMode>,
);