import react from 'react';
import { Container } from 'react-bootstrap';
import {Image} from 'react-bootstrap';

const Footer = () => {
    return (
        <footer>
            <Container style={{marginBottom:'5%'}}>
                <Image src='/img/logo.png' style={{display:'inline-block', position:'absolute', left:'22vw'}}/>
                <div style={{ color: 'gray', marginBottom:'2%', position:'absolute', left:'32vw' }}>
                    (48434) 부산 남구 수영로 309 8호관 608호 TEL : 051-663-5140 FAX : 051-- 사업자등록번호 : 000-00-00000<br/>
                    Copyright (c) KYUNGSUNG UNIVERSITY. All rights reserved. & Design Resource by Freepik
                </div>
            </Container>
        </footer>
    )
}

export default Footer;