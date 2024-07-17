import React from 'react';
import Footer from './Footer';
import Header from './Header';

const container_style = {
    fontFamily: 'arial',
    fontSize: '24px',
    margin: '4% 10% 4% 10%',
    // outline: 'dashed 1px black',
    /* 설정 */
    justifyContent: 'center',
    alignItems: 'center'
};

const Layout = ({ children }) => {
    return (
        <div>
            <Header />
            <div style={container_style}>
                {children}
            </div>
            <Footer/>
        </div>
    )
}

export default Layout;