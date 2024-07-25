import React, {Component} from 'react';
import axios from "axios";

const BOARD_API_BASE_URL = "http://localhost:8080/api/board";

class Board extends Component {
    constructor(props) {
        super(props)
        // # 1.
        this.state = {
            boards: [],
        }

    }

    async componentDidMount() {
        var res = null;
        try {
            res = await axios.get(BOARD_API_BASE_URL);

            this.setState({
                boards: res.data,
            });

        } catch (ex) {

        }

    }

    // # 3.
    render() {
        return (
            <div>
                <h2 className="text-center">게시판 목록</h2>
                <div className="row">
                    <a className="btn btn-primary" href="#/Board/Post"> 글 작성</a>
                </div>
                <div className="row">
                    <table className="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th>글 번호</th>
                            <th>타이틀</th>
                            <th>작성자</th>
                            <th>작성일</th>
                            <th>갱신일</th>
                            <th>조회수</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.boards ?
                                this.state.boards.map(
                                    board =>
                                        <tr key={board.id}>
                                            <td> {board.id} </td>
                                            <td> {board.title} </td>
                                            <td> {board.writer} </td>
                                            <td> {board.createdDate} </td>
                                            <td> {board.updatedDate} </td>
                                            <td> {board.count} </td>
                                        </tr>
                                ) : ""
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}

export default Board;