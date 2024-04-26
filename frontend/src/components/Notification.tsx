import React, { useEffect, useState } from 'react';
import { Alert, AlertIcon } from '@chakra-ui/react';

const Notification = ({ text, type }) => {
  const [isVisible, setIsVisible] = useState(true);

  useEffect(() => {
    const timeout = setTimeout(() => {
      setIsVisible(false);
    }, 5000);

    return () => clearTimeout(timeout);
  }, []);

  
  console.log(isVisible)

  return (
    <div className='notification' style={{position: 'absolute', width: '50%', justifyContent: 'center', top: '0', left: '50%'}}>
      {isVisible && (
        <Alert status={type} onClose={() => setIsVisible(false)}>
          <AlertIcon />
          {text}
        </Alert>
      )}
    </div>
  );

};

export default Notification;