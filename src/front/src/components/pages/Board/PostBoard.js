import React, {Component} from 'react';
import axios from "axios";
const BOARD_API_BASE_URL = "http://localhost:8080/api/board";


class PostBoard extends Component {
    constructor(props) {
        super(props);

        this.state = {
            title: '',
            writer: '',
            content: ''
        }

        this.changeTitleHandler = this.changeTitleHandler.bind(this);
        this.changeContentHandler = this.changeContentHandler.bind(this);
        this.changeWriterHandler = this.changeWriterHandler.bind(this);
        this.createBoard = this.createBoard.bind(this);
    }

    changeTitleHandler = (event) => {
        this.setState({title: event.target.value});
    }

    changeContentHandler = (event) => {
        this.setState({content: event.target.value});
    }

    changeWriterHandler = (event) => {
        this.setState({writer: event.target.value});
    }

    async createBoard(event)  {
        event.preventDefault();

        if (!this.state.title || !this.state.writer || !this.state.content) {
            alert("내용을 작성해주세요!");
        } else {

            let board = {
                title: this.state.title,
                content: this.state.content,
                writer: this.state.writer
            };

            board = await axios.post(BOARD_API_BASE_URL, board);
            alert("작성이 완료되었습니다!");
            window.location.href = "#/Board";

        }
    };

    render() {
        return (
            <div>
                <div className="container">
                    <div className="row">
                        <div className="card col-md-6 offset-md-3 offset-md-3">
                            <h3 className="text-center">새 글을 작성해주세요</h3>
                            <div className="card-body">
                                <form>
                                    <div className="form-group">
                                        <label> 제목 </label>
                                        <input type="text" placeholder="title" name="title" className="form-control"
                                               value={this.state.title} onChange={this.changeTitleHandler}/>
                                    </div>
                                    <div className="form-group">
                                        <label> Contents </label>
                                        <textarea placeholder="contents" name="contents" className="form-control"
                                                  value={this.state.content} onChange={this.changeContentHandler}/>
                                    </div>
                                    <div className="form-group">
                                        <label> MemberNo </label>
                                        <input placeholder="memberNo" name="memberNo" className="form-control"
                                               value={this.state.writer} onChange={this.changeWriterHandler}/>
                                    </div>
                                    <button className="btn btn-success" onClick={this.createBoard}>Save</button>
                                    <a className="btn btn-danger" href="/#/Board"
                                            style={{marginLeft: "10px"}}>Cancel
                                    </a>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        );
    }
}

export default PostBoard;