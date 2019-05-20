import React, { Component, FormEvent } from 'react'
import './Login.css';
import logo from './images/logo/logov1.png';
import defaultAvatar from './../images/default.png';
import Header from './../Header';
import Footer from './../Footer';
import axios from 'axios';
import { any } from 'prop-types';
import { Redirect } from 'react-router';
export default class Login extends Component<any, any> {
  userName: any;
  passWord: any;
  constructor(props:any){
    super(props);
    this.passWord = "";
    this.userName = "";
    this.state = {authentication: false, RedirectToHome: false};
  }
  componentDidMount(){
    if(localStorage.getItem("JWT") != null && localStorage.getItem("JWT") != undefined){
      this.setState({RedirectToHome: true});
    }
  }
  submitForm(e:React.FormEvent<HTMLFormElement>){
    // var headers = {'Content-Type': 'application/json' };
    var info:any = {};
    var self = this;
    info["userName"] = this.userName.value;
    info["passWord"] = this.passWord.value;
    axios.post("http://localhost:8080/login",info)
    .then(function(resp){
      if(resp.status == 200){
        var jwt: any = resp.data;
        localStorage.setItem("JWT", jwt);
        self.setState({authentication: true});
        e.preventDefault();
      }else{
        console.log("not authenticated");
        e.preventDefault();
      }
      
    });
    e.preventDefault();
  }
  render() {
    if(this.state.authentication === true || this.state.RedirectToHome === true){
      console.log("true");
      return(<Redirect to="/Home"/>)
    }
    console.log("false");
    return (
        <div>
        <meta charSet="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta httpEquiv="X-UA-Compatible" content="ie=edge" />
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossOrigin="anonymous" />
        <link rel="stylesheet" href="/assets/fonts/flaticons/flaticon.css" />
        <title>صفحه ی ورود</title>
        <Header/>
        <main style={{backgroundColor: "#C1EEF3"}}>
          <div className="loginbox">
            <img src={defaultAvatar} className="avatar" />
            <h1 style={{margin: 0, padding: '0 0 20px', textAlign: 'center', fontSize: '22px'}}>وارد شوید</h1>
            <form onSubmit = {e => this.submitForm(e)}>
              <p>نام کاربری</p>
              <input type="text" onBlur={(e) => e.target.placeholder = "نام کاربری را وارد کنید"}
               onFocus={(e) => e.target.placeholder = ""} placeholder="نام کاربری را وارد کنید"  ref={(input) => this.userName = input}/>
              <p>رمز عبور</p>
              <input type="password" onBlur={(e) => e.target.placeholder = "رمز عبور را وارد کنید"}
               onFocus={(e) => e.target.placeholder = ""} placeholder="رمز عبور را وارد کنید" ref={(input) => this.passWord = input} />
              <input type="submit" value="ورود" />
            </form>
          </div>
        </main>
        <Footer/>
      </div>
    )
  }
}
