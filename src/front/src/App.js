import 'bootstrap/dist/css/bootstrap.min.css';
import { Routes, Route, HashRouter } from 'react-router-dom';
import React from "react";
import Home from './components/pages/Home';
import Layout from './components/Layout/Layout';

import FreshMan_Semester_1st from "./components/pages/Freshman/FreshMan_Semester_1st";
import FreshMan_Semester_2nd from "./components/pages/Freshman/FreshMan_Semester_2nd";
import Sophomore_Semester_1st from "./components/pages/Sophomore/Sophomore_Semester_1st";
import Sophomore_Semester_2nd from "./components/pages/Sophomore/Sophomore_Semester_2nd";
import Junior_Semester_1st from "./components/pages/Junior/Junior_Semester_1st";
import Junior_Semester_2nd from "./components/pages/Junior/Junior_Semester_2nd";
import Senior_Semester_1st from "./components/pages/Senior/Senior_Semester_1st";
import Senior_Semester_2nd from "./components/pages/Senior/Senior_Semester_2nd";
import CreditCalculator from "./components/pages/Etc/CreditCalculator";
import Study from "./components/pages/Etc/Study";
import UsedTrade from "./components/pages/Etc/UsedTrade";
import LoginForm from "./components/pages/Login/LoginForm";
import FreshmanBoard from "./components/pages/Board/FreshmanBoard";
import SophomoreBoard from "./components/pages/Board/SophomoreBoard";
import JuniorBoard from "./components/pages/Board/JuniorBoard";
import SeniorBoard from "./components/pages/Board/SeniorBoard";


function App() {
    return (
        <Layout>
            <HashRouter>
                <Routes>
                    <Route path="/" element={<Home />} />
                    {/*1학년*/}
                    <Route path="/Freshman/FreshMan_Semester_1st" element={<FreshMan_Semester_1st />} />
                    <Route path="/Freshman/FreshMan_Semester_2nd" element={<FreshMan_Semester_2nd />} />
                    
                    {/*2학년*/}
                    <Route path="/Sophomore/Sophomore_Semester_1st" element={<Sophomore_Semester_1st />} />
                    <Route path="/Sophomore/Sophomore_Semester_2nd" element={<Sophomore_Semester_2nd />} />
                    
                    {/*3학년*/}
                    <Route path="/Junior/Junior_Semester_1st" element={<Junior_Semester_1st />} />
                    <Route path="/Junior/Junior_Semester_2nd" element={<Junior_Semester_2nd />} />

                    {/*4학년*/}
                    <Route path="/Senior/Senior_Semester_1st" element={<Senior_Semester_1st />} />
                    <Route path="/Senior/Senior_Semester_2nd" element={<Senior_Semester_2nd />} />

                    {/*게시판*/}
                    <Route path="/Board/FreshmanBoard" element={<FreshmanBoard />} />
                    <Route path="/Board/SophomoreBoard" element={<SophomoreBoard />} />
                    <Route path="/Board/JuniorBoard" element={<JuniorBoard />} />
                    <Route path="/Board/SeniorBoard" element={<SeniorBoard />} />

                    {/*기타유틸*/}
                    <Route path="/Etc/CreditCalculator" element={<CreditCalculator />} />
                    <Route path="/Etc/Study" element={<Study />} />
                    <Route path="/Etc/UsedTrade" element={<UsedTrade />} />

                    {/*로그인*/}
                    <Route path="/Login/LoginForm" element={<LoginForm />} />
                </Routes>
            </HashRouter>
        </Layout>
    );
}
export default App;