import 'bootstrap/dist/css/bootstrap.min.css';
import { Routes, Route, HashRouter } from 'react-router-dom';
import React from "react";
import Home from './components/pages/Home';
import Layout from './components/Layout/Layout';

import CreditCalculator from "./components/pages/Etc/CreditCalculator";
import Study from "./components/pages/Etc/Study";
import UsedTrade from "./components/pages/Etc/UsedTrade";
import Board from "./components/pages/Board/Board";
import PostBoard from "./components/pages/Board/PostBoard";
import Files from "./components/pages/Files/Files";
import SignUp from "./components/pages/Login/SignUp";
import LoginHome from "./components/pages/Login/LoginHome";


function App() {
    return (
        <Layout>
            <HashRouter>
                <Routes>
                    <Route path="/" element={<Home/>}/>
                    {/*자료게시판*/}
                    <Route path="/Files" element={<Files />} />

                    {/*자유게시판*/}
                    <Route path="/Board" element={<Board />} />
                    <Route path="/Board/post" element={<PostBoard />} />

                    {/*기타유틸*/}
                    <Route path="/Etc/CreditCalculator" element={<CreditCalculator />} />
                    <Route path="/Etc/Study" element={<Study />} />
                    <Route path="/Etc/UsedTrade" element={<UsedTrade />} />

                    {/*로그인*/}
                    <Route path="/Login/SignUp" element={<SignUp />} />
                    <Route path="/Login/LoginHome" element={<LoginHome />} />
                </Routes>
            </HashRouter>
        </Layout>
    );
}
export default App;