import React, { Component } from 'react'
import './App.css';
export default class Footer extends Component {
  render() {
    return (
      <div>
        <footer style={{display: "flex", justifyContent: "center", alignItems: "center"}}>
            <span style={{fontSize: "1.3rem"}}>© تمامی حقوق این سایت متعلق به جاب‌اونجا می‌باشد</span>
        </footer>
      </div>
    )
  }
}
