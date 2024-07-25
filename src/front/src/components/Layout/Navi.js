import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import {NavItem, NavLink} from "react-bootstrap";

function Navi() {

  const base = process.env.PUBLIC_URL + '/#';

  return (
      <Navbar variant="light" bg="light">
        <Container>
          <Navbar.Brand href={base}><b>경성대 소프트웨어학과</b></Navbar.Brand>
          <Navbar.Toggle aria-controls="navbar-dark-example"/>
          {/*자료*/}
          <Navbar.Collapse id="Files">
            <Nav>
              <NavLink href={base + "/Files"}>자료</NavLink>
            </Nav>
          </Navbar.Collapse>

          {/*게시판*/}
          <Navbar.Collapse id="Board">
            <Nav>
              <NavLink href={base + "/Board"}>자유게시판</NavLink>
            </Nav>
          </Navbar.Collapse>

          {/*기타유틸*/}
          <Navbar.Collapse id="Etc">
            <Nav>
              <NavDropdown id="EtcUtils" title="기타 유틸" menuVariant="dark">
                <NavDropdown.Item href={base + "/Etc/CreditCalculator"}>학점계산기</NavDropdown.Item>
                <NavDropdown.Item href={base + "/Etc/Study"}>스터디</NavDropdown.Item>
                <NavDropdown.Item href={base + "/Etc/UsedTrade"}>중고거래</NavDropdown.Item>
              </NavDropdown>
            </Nav>
          </Navbar.Collapse>

          {/*로그인*/}
          <Navbar.Collapse>
            <Nav>
              <NavLink href={base + "/Login/LoginHome"}>로그인</NavLink>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
  );
}

export default Navi;