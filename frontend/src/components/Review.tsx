

interface ReviewProps {
  id: number
  text: string
  rating: number

}

function Review({ id, text, rating}:ReviewProps) {

  return (
    <div className='review'>
        <h3>{rating}</h3>
        <p>{text}</p>
    </div>
  )
}

export default Review