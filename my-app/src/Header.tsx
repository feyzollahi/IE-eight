import React, { Component } from 'react'
import './App.css';
import logo from './images/logo/logov1.png';
import { Link } from 'react-router-dom';

export default class Header extends Component {
  removeJWT(e:any){
    console.log("removeJWT");
    if(localStorage.getItem("JWT") != undefined && localStorage.getItem("JWT") != null){
      localStorage.removeItem("JWT");
    }
  }
  render() {
    return (
      <div>
        <header>
          <div className="container h-100">
            <div id="header" className="row align-items-center justify-content-between">
              <a id="logo" className="col-auto d-flex align-items-center" href="/Home"><img src={logo} alt="logo" /></a>
              <nav className="col-auto row align-items-center">
                <div id="profile" className="col-auto">
                  <Link to="/Profile" style={{fontSize: "2rem"}}>حساب کاربری</Link>
                </div>
                <div id="logout" className="col-auto">
                  <a onClick={e => this.removeJWT(e)} href="/Login" style={{fontSize: "2rem"}}>خروج</a>
                </div>
              </nav>
            </div>
          </div>
        </header>
      </div>
    )
  }
}
