import React, { Component, isValidElement } from 'react'
import './SignUp.css';
import Header from './../Header';
import Footer from './../Footer';
import logo from './../images/logo/logov1.png'
import firstImage from './../images/images.png';
import secondImage from './../images/job.jpg';
import thirdImage from './../images/job4_f.jpg';
import axios from 'axios';
import ReactDom from "react-dom";
import 'react-notifications/lib/notifications.css';
import NotificationSystem from 'react-notification-system';

export default class SignUp extends Component {
  // NotificationSystem = require('react-notification-system');
  _notificationSystem: any = null;
  componentDidMount = () => {
    this._notificationSystem = this.refs.notificationSystem;

  }
  firstName: any;
  lastName: any;
  userName: any;
  passWord: any;
  passWordConfirm: any;
  jobTitle: any;
  bio: any;
  profileImageLink: any;
  constructor(props: any){
    super(props);

  }
  areSamePassWordAndPassWordConfirm(info:any): boolean{
    console.log("passWord = " + info["passWord"])
    console.log("passWordConfirm = " + info["passWordConfirm"])
    if(info["passWord"] != info["passWordConfirm"]){
        return false;
    }
    return true;
  }
  isThereEmptyField(info:any): boolean{
    console.log("salam");
    if(info["firstName"] != "" && info["lastName"] != "" 
        && info["userName"] != "" && info["passWord"] != ""
        && info["bio"] != "" && info["jobTitle"] != "" && info["profileImageLink"] != ""){

      return false;
     }

     return true;
  }
  checkErrors = (info: any, self:any) => {
    console.log(self.areSamePassWordAndPassWordConfirm(info));
    console.log(self.isThereEmptyField(info));
    var result:boolean = true;
    if(self.areSamePassWordAndPassWordConfirm(info) === false){
      self._addNotification("تکرار کلمه عبور با کلمه عبور فرق دارد", "error");
      result = false;
    }
    if(self.isThereEmptyField(info) === true){
      self._addNotification("لطفا تمام قسمت ها را پر کنید", "error");
      result = false;
    }
    return result;
  }
  _addNotification(message: string, level:string): void {
    this._notificationSystem.addNotification({
      message: message,
      level: level
    });
  }
  submitForm(e: React.FormEvent<HTMLFormElement>): void{
    var info:any = {};
    info["firstName"] = this.firstName.value;
    info["lastName"] = this.lastName.value;
    info["userName"] = this.userName.value;
    info["passWord"]= this.passWord.value;
    info["passWordConfirm"] = this.passWordConfirm.value;
    info["jobTitle"] = this.jobTitle.value;
    info["bio"] = this.bio.value;
    info["profileImageLink"] = this.profileImageLink.value;
    console.log(info);
    var notification =  require('react-notifications');
    if(this.checkErrors(info, this) === false){
      e.preventDefault();
      return;
    }
    

    var self = this;
    axios.post("http://localhost:8080/signUp", info)
       .then(function(resp){
        if(resp.status == 200){
          self._addNotification("فرایند ثبت نام با موفقیت انجام شد", "success") ;          
        }
        else if(resp.status == 403){
          self._addNotification("این نام کاربری توسط فرد دیگری انتخاب شده است", "error");
        }
    });
    e.preventDefault();
  }
  render() {
    return (
      <div>
        <div>
        <meta charSet="UTF-8" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="fonts/iransans-fonts/fonts.scss" />
        <link rel="stylesheet" type="text/css" href="mother.css" />
        <link rel="stylesheet" type="text/css" href="signUp.css" />
        <title>Title</title>
        <style dangerouslySetInnerHTML={{__html: "\n        body{\n            font-family: IRANSans;\n        }\n    " }} />
        <div style={{height: '100vh'}} >
          <Header/>
          <div className="jobOonjaMiddleBackGroundUser">
          </div>
          <div className="jobOonjaSignUpBody container-fluid">
            <div id="slideshow" style={{marginTop: '-60px'}}>
              <div className="slide-wrapper">
                <div className="slide"> <img src={firstImage} className="sel-image" /></div>
                <div className="slide"><img src={secondImage} className="sel-image" /></div>
                <div className="slide"><img src={thirdImage} className="sel-image" /></div>
                <div className="slide"><img src={thirdImage} className="sel-image" /></div>
              </div>
            </div>
            <div className="signup">
              <form onSubmit = {e => this.submitForm(e)}>
                <div className="myform">
                  <p>نام</p>
                  <input type="text" name="firstName" id="firstName" ref={(input) => this.firstName = input} />
                </div>
                <div className="myform">
                  <p> نام خانوادکی</p>
                  <input type="text" name="lastName"  id="lastName" ref={(input) => this.lastName = input}/>
                </div>
                <div className="myform">
                  <p>نام کاربری</p>
                  <input type="text" name="userName"  id="userName" ref={(input) => this.userName = input}/>
                </div>
                <div className="myform">
                  <p>کلمه عبور</p>
                  <input type="password" name="passWord"  is="passWord" ref={(input) => this.passWord = input}/>
                </div>
                <div className="myform">
                  <p>تکرار کلمه عبور</p>
                  <input type="password" name="passWordConfirm"  id= "passWordConfirm" ref={(input) => this.passWordConfirm = input} />
                </div>
                <div className="myform">
                  <p>عنوان شغلی</p>
                  <input type="text" name="jobTitle"  id="jobTitle" ref={(input) => this.jobTitle = input}/>
                </div>
                <div className="myform">
                  <p>آدرس عکس پروفایل</p>
                  <input type="text" name="profileImageLink" id="profileImageLink" ref={(input) => this.profileImageLink = input}/>
                </div>
                <div className="bio">
                  <p>بیوگرافی</p>
                  <textarea  id="bio" ref={(input) => this.bio = input} style={{height: '100%', width: '500px'}} placeholder="خودتان را معرفی کنید" defaultValue={""} />
                </div>
                <div className="submit2">
                  <input type="submit" defaultValue="ثبت نام" />
                </div>
              </form> 
            </div>
            <div className="discription-div">
            </div>
            <NotificationSystem ref="notificationSystem" />

            <Footer/>
          </div>
        </div></div>
      </div>
    )
  }
}
