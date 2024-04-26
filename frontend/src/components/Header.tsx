import logo from '/logo.png';
import { Button } from '@chakra-ui/react';
import '../style/Header.css';
import { Link, useLocation } from 'react-router-dom';
import {useState, useEffect} from 'react'
import { useAuth } from './LoginForm'

function Header() {
  const { authHeader, setAuthHeader } = useAuth();
  const location = useLocation();
  const [userRole, setUserRole] = useState<string | null>(null);


  useEffect(() => {

    const storedRole = localStorage.getItem('role');
    setUserRole(storedRole);
  }, []);

  const renderLogoutButton = () => {
    if (location.pathname !== '/') {
      return <Link to='/'>
      <Button colorScheme='red' onClick={handleLogout}>Log out</Button>
    </Link>;
    }
    return null;
  };

  
  const usePathname = () => {
    const location = useLocation();
    return location.pathname;
  }

  console.log(location)


  const handleLogout = () => {
    setAuthHeader(null);
    localStorage.removeItem("role");
    localStorage.removeItem("authHeader");
  };

  return (
    <div className='header' style={{position : location.pathname === '/' ? 'absolute' : 'static'}}>
    <div className='left-section'>
      <div className='menu'>
        <Link to='/main'>
          <img src={logo} className='logo' alt='App logo' />
        </Link>
      </div>
    </div>
    <div className='right-section'>
    {location.pathname !== '/' && (
            <Link to='/profile'>
              <Button colorScheme='green'>
                Profile
              </Button>
            </Link>
          )}
    {renderLogoutButton()}
    
    </div>
  </div>
  );
}
export default Header;