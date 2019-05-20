import React, { Component } from 'react'
import Header from './../Header';
import ProjectSkillItem from './ProjectSkillItem';
import './Project.css';
import './../App.css';
import './../bootstrap.css';
import red_deadline from './../images/icons/red-deadline.png';
import red_danger from './../images/icons/red-danger.png';
import black_money_bag from './../images/icons/money_bag.png';
import green_tick from './../images/icons/green-tick.png';
import black_deadline from './../images/icons/deadline.png';
import black_danger from './../images/icons/danger.png';
import check_mark from './../images/icons/check-mark.png';
import blue_money_bag from './../images/icons/blue-money-bag.png';
import Footer from './../Footer';
import faker from 'faker';
import axios from 'axios';
import { Redirect } from 'react-router';
// import persianJs from 'persianjs';
export default class Project extends Component<any, State> {
    static projectDataJson: any = "projectTitle";
    static timeRemainForBid:number[];
    static projectSkillItemsJSX: any = [];
     getProjectFooterInBidNotSendAndTimeRemain = () => {return (<div className="project-bid">
                                                                    <h3 className="project-bid-offer-hard-name">ثبت پیشنهاد</h3>
                                                                    <div className="project-offer">
                                                                    <form onSubmit={e => this.submitForm(e)}>
                                                                        <div className="project-input-buffer-div">
                                                                            <input className="project-bid-amount-input" onChange={e => this.handleBidInputChange(e)} type="text" name="project-offer" placeholder="پیشنهاد خود را وارد کنید" />
                                                                            <p className="project-toman-hard-name">تومان</p>
                                                                        </div>
                                                                        <input type="submit" value="ارسال" className="project-bid-submit-button" />
                                                                    </form>
                                                                    </div>
                                                                </div>);}
    getProjectFooterInBidTimeExpired = () => {
        return (<div className="project-bid">
                    <img className="project-icons" src={red_danger} alt="time finished" />
                    <p className="project-bid-response-pragraph-time-expired">مهلت ارسال پیشنهاد برای این پروژه به پایان رسیده است!</p>
                </div>);
                                            }
    getProjectFooterInBidSentBefore = () => {
        return (<div className="project-bid">
                    <img className="project-icons" src={green_tick} alt="تایید" />
                    <p className="project-bid-response-pragraph-sent-before">شما قبلا پیشنهاد خود را به ثبت رسانده اید</p>
                </div>);
                                        }
    getProjectFooterInBidSentNow = () => {
        return (<div className="project-bid">
                    <img className="project-icons" src={green_tick} alt="تایید" />
                    <p className="project-bid-response-pragraph-sent-before">شما با موفقیت پیشنهاد خود را به ثبت رساندید</p>
                </div>);
    }

    constructor(props: any){
        super(props);
        console.log("path: " + this.props.location.pathname);
        var pathName:string = this.props.location.pathname;
        var split = pathName.split("/");
        var projectId = split[2];
        this.state = {bidState: "not-yet", humanTimeRemain: [],
         budget: 0, description: "", projectSkills: [],
          projectSkillItemsJSX: null, imageUrlTxt: ""
        , bidInput: "", humanTimeRemainStr: "", RedirectToLogin: false};
          var self = this;
          var header:any = {"Authorization": localStorage.getItem("JWT")}

          axios.get("http://localhost:8080/showSpecifiedProjectCtrl",{
              params: {
                  projectId: projectId
              },
              headers:{
                header
              }
          })
          .then(function(resp){
            if(resp.status == 401 || resp.status == 403){
              self.setState({RedirectToLogin: true});
              return;
            }
              console.log(resp);
              Project.projectDataJson = resp.data;
              var date = new Date();
              var time = date.getTime();
              var projectSkills:ProjectSkill[] = Project.projectDataJson.skills;
              projectSkills.forEach(function(projectSkill){
                Project.projectSkillItemsJSX.push(<ProjectSkillItem skillName={projectSkill.name} skillRate={projectSkill.point}/>);
              });
              console.log(Project.projectSkillItemsJSX);
              self.setState({budget: Project.projectDataJson.budget,
                 description: Project.projectDataJson.description,
                projectSkillItemsJSX: Project.projectSkillItemsJSX,
                imageUrlTxt: Project.projectDataJson.imageUrlText});
              
              if(time > Project.projectDataJson.deadline){
                  self.setState({bidState: "bid-time-expired", humanTimeRemainStr: "مهلت تمام شده"});
                  console.log("bid-time-expired");
              }
              else{
                self.setState({humanTimeRemain: Project.miliSecondToHumanTime(Project.projectDataJson.deadline - time), humanTimeRemainStr: Project.humanTimeToStr(Project.miliSecondToHumanTime(Project.projectDataJson.deadline - time))});
                console.log(self.state.humanTimeRemainStr + " new");
                if(Project.projectDataJson.userBid === true){
                    self.setState({bidState: "bid-in-past"});
                }
                else{
                    self.setState({bidState: "not-yet"});
                }
             }
             console.log(self.state.bidState);
              
          }).catch(error => {
              console.log(error);
          });
        }
        static miliSecondToHumanTime = (miliseconds:number) => {
            var allSeconds: any = miliseconds / 1000;
            allSeconds = parseInt(allSeconds, 10);
            var day:any = ((allSeconds) / 3600) / 24;
            day = parseInt(day, 10);
            var daySeconds:number = day * 24 * 3600;
            var hour:any = (allSeconds - daySeconds) / 3600;
            hour = parseInt(hour, 10);
            const hourSecond:number = hour * 3600;
            var minute:any = (allSeconds - daySeconds - hourSecond) / 60;
            minute = parseInt(minute, 10);
            const minuteSecond:number = minute * 60;
            var second:any = (allSeconds - daySeconds - hourSecond - minuteSecond);
            second = parseInt(second, 10);
            return [day, hour, minute, second]; 
        }
        static humanTimeToStr = (humanTimeArr:number[]) => {
            if(humanTimeArr.length == 0){
                return "";
            }
            var persianJs = require('persianjs');

            var dayStr = persianJs(humanTimeArr[0].toString()).englishNumber().toString();
            var hourStr = persianJs(humanTimeArr[1].toString()).englishNumber().toString();
            var minuteStr = persianJs(humanTimeArr[2].toString()).englishNumber().toString();
            var secondStr = persianJs(humanTimeArr[3].toString()).englishNumber().toString();;
            var timeStr:string = "زمان :";
            if(humanTimeArr[0] != 0){
                timeStr = dayStr + " روز ";
                timeStr += " و ";
            }
            if(humanTimeArr[1] != 0){
                timeStr += hourStr + " ساعت ";
                timeStr += " و ";

                console.log(timeStr);
            }
            if(humanTimeArr[2] != 0){
                timeStr +=  minuteStr + " دقیقه ";
                timeStr += " و ";

            }
            timeStr +=  secondStr + " ثانیه ";
            return timeStr;
        }
        handleBidInputChange(e: React.ChangeEvent<HTMLInputElement>): void{
            this.setState({bidInput: e.target.value});
        }
        submitForm(e: React.FormEvent<HTMLFormElement>): void{
            var self = this;
            var pathName:string = this.props.location.pathname;
            var split = pathName.split("/");
            var projectId = split[2];
            var header:any = {"Authorization": localStorage.getItem("JWT")}

            axios.get("http://localhost:8080/userBidProjectCtrl",{
                params: {
                    projectId: projectId,
                    bidAmount: self.state.bidInput
                },
                headers:{
                  header
                }
            })
            .then(function(resp){
                if(resp.status == 200){
                    self.setState({bidState: "bid-send-now"});
                    console.log("bid-send");
                    axios.get("http://localhost:8080/showSpecifiedProjectCtrl",{
                        params: {
                            projectId: projectId
                        }
                    })
                    .then(function(resp){
                        console.log(resp);
                    });
                }
                else if(resp.status == 401 || resp.status == 403){
                  self.setState({RedirectToLogin: true});
                }
            });
            e.preventDefault();
        }
        static hs:any;
  render() {
    if(this.state.RedirectToLogin === true){
      return(<Redirect to="/Login"/>);
    }
    var persianJs = require('persianjs');

    var budgetStr = "بودجه: " + persianJs(this.state.budget.toString()).englishNumber().toString() + " تومان";
    var timeIcon: any = "";
    var humanTimeRemainStr:string = this.state.humanTimeRemainStr;
    var time_remain_class = "time-remain-prograph-time";
    var projectFooter: any;
    console.log("render call");
    switch(this.state.bidState){
        case "not-yet":
            timeIcon = black_deadline;
            projectFooter = this.getProjectFooterInBidNotSendAndTimeRemain();
            break;
        case "bid-in-past":
            timeIcon = black_deadline;
            projectFooter = this.getProjectFooterInBidSentBefore();
            break;
        case "bid-time-expired":
            timeIcon = red_deadline;
            humanTimeRemainStr: "مهلت تمام شده";
            time_remain_class = "time-expired-prograph-time";
            projectFooter = this.getProjectFooterInBidTimeExpired();
            console.log("exp");
            break;
        case "bid-send-now":
            timeIcon = black_deadline;
            projectFooter = this.getProjectFooterInBidSentNow();
            break;
        default:
    }
    console.log("  def  " + humanTimeRemainStr);


    return (
        <div>
        <meta charSet="UTF-8" />
        <link rel="stylesheet" type="text/css" href="fonts/iransans-fonts/fonts.scss" />
        <link rel="stylesheet" type="text/css" href="mother.css" />
        <title>Project</title>
        <style dangerouslySetInnerHTML={{__html: "\n        body{\n            font-family: IRANSans;\n        }\n    " }} />
        <div style={{height: '100vh'}}>
          <Header/>
          <div className="jobOonjaMiddleBackGroundProject">
          </div>
          <div className="project-body">
            <div className="project-body-features">
              <div className="project-body-body">
                <div className="project-image-div">
                  <img className="project-image" src={this.state.imageUrlTxt} alt="project-image" />
                </div>
                <div className="project-all-details">
                  <div className="project-subject-div">
                    <p className="project-subject-prograph">{Project.projectDataJson.title}</p>
                  </div>
                  <div className="project-details-div&quot;">
                    <div className="time-remain-div">
                      <img className="project-icons" src={timeIcon} alt="deadline-icon" />
                      <p className="time-remain-prograph">{this.state.bidState != "bid-time-expired" ? "زمان باقی مانده:": ""}</p><p className={time_remain_class}> {humanTimeRemainStr} </p><p />
                    </div>
                    <div className="project-value-div">
                      <img className="project-icons" src={blue_money_bag} alt="money-bag-icon" />
                      <p className="project-value-prograph">{budgetStr}</p>
                    </div>
                  </div>
                  <div className="project-description">
                    <div className="project-description-header-div">
                      <p className="project-description-header-pragraph">توضیحات</p>
                    </div>
                    <div className="project-description-content-div">
                      <p className="project-description-content-pragraph">{this.state.description}</p>
                    </div>
                  </div>
                </div>
              </div>
              <div className="project-skill">
                <div className="signUpPragraph-div">
                  <h3 className="signUpPragraph">مهارت های لازم:</h3>
                </div>
                <div className="user-skills">
              <ul className="skills-list">
                {this.state.projectSkillItemsJSX}
              </ul>
            </div>
              </div>
              <div className="project-footer">
                    {projectFooter}
              </div>
            </div>
          </div>
          <Footer/>
        </div>
      </div>
    )
  }
}

interface Props{

}
interface State{
    bidState: string;
    humanTimeRemain:number[];
    humanTimeRemainStr: string;
    budget:number;
    description: string;
    projectSkills : ProjectSkill[];
    projectSkillItemsJSX: any;
    imageUrlTxt: string;
    bidInput: string;
    RedirectToLogin: boolean;
}
interface ProjectSkill{
    name: string;
    point: number;
}