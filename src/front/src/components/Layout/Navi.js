import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';

function Navi() {

  const base = process.env.PUBLIC_URL + '/#';

  return (
    <Navbar variant="light" bg="light">
      <Container>
        <Navbar.Brand href={base}><b>경성대 소프트웨어학과</b></Navbar.Brand>
        <Navbar.Toggle aria-controls="navbar-dark-example" />
        {/*1학년*/}
        <Navbar.Collapse id="FreshmanData">
          <Nav>
            <NavDropdown id="Freshman" title="1학년" menuVariant="dark">
              <NavDropdown.Item href={base + "/Freshman/FreshMan_Semester_1st"}>1학기</NavDropdown.Item>
              <NavDropdown.Item href={base + "/Freshman/FreshMan_Semester_2nd"}>2학기</NavDropdown.Item>
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
        {/*2학년*/}
        <Navbar.Collapse id="Sophomore">
          <Nav>
            <NavDropdown id="SophomoreData" title="2학년" menuVariant="dark">
              <NavDropdown.Item href={base + "/Sophomore/Sophomore_Semester_1st"}>1학기</NavDropdown.Item>
              <NavDropdown.Item href={base + "/Sophomore/Sophomore_Semester_2nd"}>2학기</NavDropdown.Item>
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
        {/*3학년*/}
        <Navbar.Collapse id="Junior">
          <Nav>
            <NavDropdown id="JuniorData" title="3학년" menuVariant="dark">
              <NavDropdown.Item href={base + "/Junior/Junior_Semester_1st"}>1학기</NavDropdown.Item>
              <NavDropdown.Item href={base + "/Junior/Junior_Semester_2nd"}>2학기</NavDropdown.Item>
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
        {/*4학년*/}
        <Navbar.Collapse id="Senior">
          <Nav>
            <NavDropdown id="Seniordata" title="4학년" menuVariant="dark">
              <NavDropdown.Item href={base + "/Senior/Senior_Semester_1st"}>1학기</NavDropdown.Item>
              <NavDropdown.Item href={base + "/Senior/Senior_Semester_2nd"}>2학기</NavDropdown.Item>
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
        {/*게시판*/}
        <Navbar.Collapse id="Board">
          <Nav>
            <NavDropdown id="BulletinBoard" title="자유게시판" menuVariant="dark">
              <NavDropdown.Item href={base + "/Board/FreshmanBoard"}>1학년</NavDropdown.Item>
              <NavDropdown.Item href={base + "/Board/SophomoreBoard"}>2학년</NavDropdown.Item>
              <NavDropdown.Item href={base + "/Board/JuniorBoard"}>3학년</NavDropdown.Item>
              <NavDropdown.Item href={base + "/Board/SeniorBoard"}>4학년</NavDropdown.Item>
            </NavDropdown>
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
            <NavDropdown id="Login" title="로그인" menuVariant="dark">
              <NavDropdown.Item href={base + "/Login/LoginHome"}>로그인</NavDropdown.Item>
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default Navi;