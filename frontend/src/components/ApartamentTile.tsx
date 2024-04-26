import '../style/Header.css'
import '../style/ApartamentTile.css'
import {Link } from 'react-router-dom'
import { Button, Card, CardBody} from '@chakra-ui/react'
import { useState } from 'react'


interface ApartmentTileProps {
  id: number;
  name: string;
  photo: string;
}

function ApartamentTile({ id, name, photo, price}: ApartmentTileProps) {

  return (
    <>
      <Card className='apartment--tile'>
        <CardBody>
          <div className='apartment--image'>
           <img src={photo}></img>
          </div>
          <div className='details' style={{margin: '0.5vw'}}>
            <h2><b>{name}</b></h2>
            <h3>{price} $ per night</h3>
          </div>
          <Link to={`/apartment/${id}`}>
            <Button style={{backgroundColor:'#001650', color: 'white'}}>Book now</Button>
          </Link>
        </CardBody>
      </Card>
    </>
  );
}

export default ApartamentTile;