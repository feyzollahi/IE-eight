import React, { Component } from 'react'
import './ProjectSummaryItem.css';
import Project from'./../Project/Project';
import faker from 'faker';
import ProjectSummaryItemSkill from './ProjectSummaryItemSkill';
import {BrowserRouter as Router, Route, Link} from 'react-router-dom';

export default class ProjectSummaryItem extends Component<Props, State> {
    constructor(props: Props){
        super(props);
        this.state = {skillsJSX: [], humanDeadline: "", remainTimeCssClass: ""};
        var skillJSXArr : any[] = []
        this.props.skills.forEach(function(skill : any){
          skillJSXArr.push(<ProjectSummaryItemSkill name={skill.name}/>);
        });
        var self = this;
        var humanTimeStr:string = "";
        var remainTimeCssClass: string= "project-time";
        if(this.props.deadline - new Date().getTime() < 0){
          humanTimeStr = "مهلت تمام شده";
          remainTimeCssClass = "project-expired-time";
        }
        else{
          remainTimeCssClass = "project-time";
          var humanTime = Project.miliSecondToHumanTime(this.props.deadline - new Date().getTime());
          var persianJs = require('persianjs');
          humanTimeStr += humanTime[0] == 0 ? persianJs("0").englishNumber().toString()
          : persianJs(humanTime[0]).englishNumber().toString();
          humanTimeStr += " روز و ";
          humanTimeStr += humanTime[1] == 0 ? "" + persianJs("00").englishNumber().toString():
          "" + persianJs(humanTime[1]).englishNumber().toString();
          humanTimeStr += humanTime[2] == 0 ? ":" + persianJs("00").englishNumber().toString():
          ":" + persianJs(humanTime[2]).englishNumber().toString();
          humanTimeStr += humanTime[3] == 0 ? ":" + persianJs("00").englishNumber().toString():
          ":" + persianJs(humanTime[3]).englishNumber().toString();
        }
        setTimeout(function(){
          self.setState({skillsJSX: skillJSXArr, humanDeadline: humanTimeStr, remainTimeCssClass: remainTimeCssClass});
        }, 10)
    }
  render() {
    return (
      <Link to={`/Projects/${this.props.id}`}>
        <div className="project-summary-mother-div">
          <div className="main-window">
            <div className="rtl img-div">
              <img className="project-image" src={this.props.imageUrlText}/>
              
            </div>
            <div className="project-info">
              <div className="project-title-time">
                <p className="project-title">{this.props.title}</p>
                <p className={this.state.remainTimeCssClass}>
                  {this.state.remainTimeCssClass == "project-time" ?
                  "زمان باقی مانده: " + this.state.humanDeadline:
                  this.state.humanDeadline}
                </p>
              </div>
              <p className="project-item-description">{this.props.description}</p>
              <p className="project-budget">{"بودجه:" + this.props.budget + "تومان"}</p>
              <div className="project-item-skills">
                <p>مهارت ها:</p>
                <div className="project-item-skill-items">
                    {this.state.skillsJSX}
                </div>
              </div>
            </div>
          </div>
        
        </div>
      </Link>
    )
  }
}
interface Props{
    deadline: number;
    budget: number;
    id: string;
    description: string;
    title: string;
    skills: string[];
    imageUrlText: string;
}
interface State{
  skillsJSX: any[];
  humanDeadline: string;
  remainTimeCssClass: string;
}