import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';

function PageCard(props) {
    const title = props.title;
    const imgsrc = props.src;
    const text = props.text;
    const link = props.link;

    return (
        <Card border="gray" style={{ width: '15rem' }}>
            <Card.Img variant="top" src={imgsrc} />
            <Card.Body style={{textAlign:'center'}}>
                <Card.Title>{title}</Card.Title>
                <Card.Text style={{fontSize : 'medium'}}>
                    {text}
                </Card.Text>
                <Button href={process.env.PUBLIC_URL+'/#'+link} variant="primary">바로가기</Button>
            </Card.Body>
        </Card>
    );
}

export default PageCard;