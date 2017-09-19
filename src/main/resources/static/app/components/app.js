import React from 'react';
import ReactDom from "react-dom";
import { Navbar, NavItem, Nav, Grid, Row, Col ,FormControl,FormGroup ,Tooltip, Popover, Modal, Button, OverlayTrigger,ControlLabel,Form} from "react-bootstrap";

class App extends React.Component {
    constructor(props) {
        super(props);
        //bind send File function
        this.sendFile = this.sendFile.bind(this);
        //create our state
        this.state = {responseValue:null,activeData:null};
    }
    sendFile(e){
        //get File
        const input = ReactDom.findDOMNode(this.refs["file"]);
        e.preventDefault();
        const data = new FormData();
        data.append('file',input.files[0]);
        //post file -> change state
        fetch('/testUpload',{method:'POST',body:data})
            .then(response=>response.json())
            .then(json=> {
                    this.setState({responseValue: json});
                }
            )
            .catch(()=>{
            console.log("FETCH ERROR")
            })
    }
    render() {
        //Conditional rendering
        let content=null;
        if (this.state.activeData=="topTenWords" && this.state.responseValue.topMatchedWords!=undefined) {
            console.log(this.state.responseValue.topMatchedWords);
            //create Table of words
            content =
                <table className="table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Word</th>
                            <th>Count</th>
                        </tr>
                    </thead>
                    <tbody>
                    {this.state.responseValue.topMatchedWords.map((item,index) =>(
                        <tr key={index}>
                            <th scope="row">{index}</th>
                            <td>{item.word}</td>
                            <td>{item.count}</td>
                        </tr>
                ))}
                    </tbody>
                </table>;
        }else if (this.state.activeData=="bracketsCheck" && this.state.responseValue.bracket!=undefined){
            //create brackets analyze result
            content = (<div>
                <h4>Brackets in text: {this.state.responseValue.bracket.test}</h4>
                <h4>Result of bracket test: {this.state.responseValue.bracket.result}</h4>
            </div>);
        }
        return (
        <div>
            <Navbar>
                <Navbar.Header>
                    <Navbar.Brand>
                        TextAnalyzer
                    </Navbar.Brand>
                </Navbar.Header>
            </Navbar>
            <Grid>
                <h4>Send .txt file</h4>
                <Row>
                    <Col md={4} sm={4}>
                    <Form inline>
                    <FormGroup>
                            <FormControl className="fileUpload" type="file" ref="file"/>
                    </FormGroup>
                    </Form>
                    </Col>
                        <Col >
                        <Button type="submit" onClick={this.sendFile}>Submit</Button>
                        </Col>
                </Row>
                <Row>
                    <Col md={4} sm={4}>
                        <h3>Select a action</h3>
                        <Nav
                            bsStyle="pills"
                            stacked
                            onSelect={eventKey => {
                                //onClick change state
                                this.setState({ activeData: eventKey});
                        }}>
                            <NavItem eventKey={"topTenWords"}>Words Analyze</NavItem>
                            <NavItem eventKey={"bracketsCheck"}>bracketsCheck</NavItem>
                        </Nav>
                    </Col>
                    <Col md={8} sm={8}>
                        <h3>Result</h3>
                        {content}
                    </Col>
                </Row>
            </Grid>
        </div>
        );
    }
}
export default App;