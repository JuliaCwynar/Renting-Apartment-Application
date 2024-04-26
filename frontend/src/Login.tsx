import './style/App.css';
import LoginForm from './components/LoginForm';


function App() {

  const loginStyle = {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    height: '100vh',
  }

  const leftStyle = {
    width: '100%',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    height: '100%'
  }

  const rightStyle = {
    backgroundColor: '#3ec8ed', 
    width: '100%',
    margin: 'auto',
    display: 'flex', 
    flexDirection: 'column', 
    justifyContent: 'center', 
    alignItems: 'center',
    height: '100%'
  }
  return (
    <>
    <div style={loginStyle}>
        <div style={leftStyle}>
          <img src='graphic.jpg' alt='login_graphic'></img>
        </div>
        <div style={rightStyle}>
          <LoginForm />
        </div>
    </div>
    </>
  );
}

export default App;