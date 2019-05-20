import React, { Component } from 'react'
import Header from './../Header';
import Footer from './../Footer';
import './Home.css';
import './../App.css';
import ProjectSummaryItem from './ProjectSummaryItem';
import UserSummaryItem from './UserSummaryItem';
import axios from 'axios';
export default class Home extends Component<any, State> {
    constructor(props: any) {
      super(props);
        var persianJs = require('persianjs');
        var firstPart = persianJs("جاب اونجا").halfSpace().toString();
        console.log(firstPart);
        var jobOonjaHeader:string = firstPart + "  خوب است!";
        console.log(jobOonjaHeader);
      this.state = {
         jobOonjaHeader: jobOonjaHeader,
         UserSummaryItemsJSX: [],
         ProjectSummaryItemsJSX: [],
         projectsJsonData: [],
         selectedProjects: [],
         selectedUsers: [],
         usersJsonData: []
      }
      var self = this;
      var header:any = {"Authorization": localStorage.getItem("JWT")}
      axios.get("http://localhost:8080/showAllUsersCtrl",{headers: header })
          .then(function(resp){
              console.log(resp);
              var userSumArr: any[] = [];
              var usersJson: UserJson[] = [];
              resp.data.forEach(function(user: any){
                var firstName = user.firstName;
                var lastName = user.lastName;
                var jobTitle = user.jobTitle;
                var userJson: UserJson = {firstName: firstName, lastName: lastName
                , jobTitle: jobTitle, userId: user.id};
                  usersJson.push(userJson); 
                userSumArr.push(<UserSummaryItem userId={user.id} name={firstName + " " + lastName} jobTitle={jobTitle}/>
                );
              });
              self.setState({UserSummaryItemsJSX: userSumArr, usersJsonData: usersJson});
          }).catch(error => {
              console.log(error);
          });
        
      axios.get("http://localhost:8080/projectlist",{headers: header })
        .then(function(resp){
            console.log(resp);
            var projectSumArr: any[] = [];
            var persianJs = require('persianjs');
            var projectsJson:ProjectJson[]  = [];
            resp.data.forEach(function(project: any){
              var projectJson: ProjectJson = {title: project.title, deadline: project.deadline
              , creationDate: project.creationDate, id: project.id, description: project.description, budget: project.budget
              , imageUrlText: project.imageUrlText, skills: project.skills};
              projectsJson.push(projectJson);
              var budget = persianJs(project.budget).englishNumber().toString();
              projectSumArr.push(<ProjectSummaryItem deadline={project.deadline}
               budget={budget} id={project.id} description={project.description}
                title={project.title} skills={project.skills}
                 imageUrlText={project.imageUrlText}/>);
            });
            projectsJson.sort(function(a, b){
              if(a.creationDate > b.creationDate)
                return -1;
              else
                return 1;
            });
            projectSumArr = [];
            projectsJson.forEach(function(projectJson){
              var budget =  persianJs(projectJson.budget).englishNumber().toString();
              projectSumArr.push(<ProjectSummaryItem deadline={projectJson.deadline}
                budget={budget} id={projectJson.id} description={projectJson.description}
                 title={projectJson.title} skills={projectJson.skills}
                  imageUrlText={projectJson.imageUrlText}/>);
            });
            console.log(projectSumArr);

            self.setState({ProjectSummaryItemsJSX: projectSumArr, projectsJsonData: projectsJson});
        }).catch(error => {
            console.log(error);
        });
    }
    searchUserWithUserName(e: any): void{
      var self = this;
      var searchVal = e.target.value;
      var usersJson: UserJson[] = this.state.usersJsonData;
      var selectedUsers:UserJson[] = [];
      var self = this;
      console.log(searchVal);
      var header:any = {"Authorization": localStorage.getItem("JWT")}
      
      axios.get("http://localhost:8080/searchUser",{
        params:{
          searchVal: searchVal
        },
        headers:{
          header
        }
      })
          .then(function(resp){
              console.log(resp);
              var userSumArr: any[] = [];
              var usersJson: UserJson[] = [];
              resp.data.forEach(function(user: any){
                var firstName = user.firstName;
                var lastName = user.lastName;
                var jobTitle = user.jobTitle;
                var userJson: UserJson = {firstName: firstName, lastName: lastName
                , jobTitle: jobTitle, userId: user.id};
                  usersJson.push(userJson); 
                userSumArr.push(<UserSummaryItem userId={user.id} name={firstName + " " + lastName} jobTitle={jobTitle}/>
                );
              });
              self.setState({UserSummaryItemsJSX: userSumArr, usersJsonData: usersJson});
          }).catch(error => {
              console.log(error);
          });
        
      


  }
    searchProjectWithProjectTitle(e: any): void{
      console.log(e);
      console.log("serachProject");
      var input: any = document.querySelector(".input-container .searchInput");
      var searchVal:string = input.value;
      console.log(searchVal);
      var self = this;
      var projectsJson: ProjectJson[] = this.state.projectsJsonData;
      var selectedProjects:ProjectJson[] = [];
      console.log(projectsJson);
      var header:any = {"Authorization": localStorage.getItem("JWT")}

      axios.get("http://localhost:8080/searchProject",{
        params: searchVal,
        headers: header
      })
        .then(function(resp){
            console.log(resp);
            var projectSumArr: any[] = [];
            var persianJs = require('persianjs');
            var projectsJson:ProjectJson[]  = [];
            resp.data.forEach(function(project: any){
              var projectJson: ProjectJson = {title: project.title, deadline: project.deadline
              , creationDate: project.creationDate, id: project.id, description: project.description, budget: project.budget
              , imageUrlText: project.imageUrlText, skills: project.skills};
              projectsJson.push(projectJson);
              var budget = persianJs(project.budget).englishNumber().toString();
              projectSumArr.push(<ProjectSummaryItem deadline={project.deadline}
               budget={budget} id={project.id} description={project.description}
                title={project.title} skills={project.skills}
                 imageUrlText={project.imageUrlText}/>);
            });
            projectsJson.sort(function(a, b){
              if(a.creationDate > b.creationDate)
                return -1;
              else
                return 1;
            });
            projectSumArr = [];
            projectsJson.forEach(function(projectJson){
              var budget =  persianJs(projectJson.budget).englishNumber().toString();
              projectSumArr.push(<ProjectSummaryItem deadline={projectJson.deadline}
                budget={budget} id={projectJson.id} description={projectJson.description}
                 title={projectJson.title} skills={projectJson.skills}
                  imageUrlText={projectJson.imageUrlText}/>);
            });
            console.log(projectSumArr);

            self.setState({ProjectSummaryItemsJSX: projectSumArr, projectsJsonData: projectsJson});
        }).catch(error => {
            console.log(error);
        });
      
    }
  render() {
    return (
        <div>
        <meta charSet="UTF-8" />
        {/*<link rel="stylesheet" type="text/css" href="fonts/iransans-fonts/fonts.scss">*/}
        <link rel="stylesheet" type="text/css" href="../signUp/signUp.css" />
        <link rel="stylesheet" type="text/css" href="home.css" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css" />
        <title>Title</title>
        <div style={{minHeight: '100vh'}}>
          <Header/>
          <div className="jobOonjaHomeMiddleBackGround">
            <div className="container">
                <div className="jobOonjaHomeBackGroundParagraphDiv">
                    <p className="jobOonjaHomeBackGroundParagraph">{this.state.jobOonjaHeader}</p>
                </div>
                <div className="searchInputDiv">
                  <div className="input-container">
                      <input type="text" className="searchInput" onFocus={(e) => {e.target.placeholder = ""}} onBlur={(e) => {e.target.placeholder = "جستجو در جاب اونجا";}} placeholder="جستجو در جاب اونجا" />
                      <div className="submitBuutonDiv">
                        <input type="submit" value="جستجو" onClick={e => this.searchProjectWithProjectTitle(e)} className="searchButton" />
                      </div>
                  </div>
                </div>
            </div>
          </div>
          <div className="user-project-list">
            <div className="user-summary-list">
              <div>
                <input className="user-name-search" onKeyUp={e => this.searchUserWithUserName(e)}
                onFocus={e => e.target.placeholder = ""}
                 onBlur={e => e.target.placeholder = "جستجو نام کاربر"} placeholder="جستجو نام کاربر"/>
              </div>
                {this.state.UserSummaryItemsJSX}

            </div>
            <div className="project-summary-list">
              {this.state.ProjectSummaryItemsJSX}
            </div>
          </div>
          <Footer/>
        </div>
      </div>
    )
  }
}

interface State{
    jobOonjaHeader:string;
    UserSummaryItemsJSX: any[];
    ProjectSummaryItemsJSX: any[];
    projectsJsonData: ProjectJson[];
    selectedProjects: SelectedProject[];
    usersJsonData: UserJson[];
    selectedUsers: UserJson[];
}
interface ProjectJson{
  deadline: number;
  creationDate: number;
  budget: number;
  id: string;
  description: string;
  title: string;
  skills: string[];
  imageUrlText: string;
}
interface SelectedProject{
  id: string;
}

interface UserJson{
  firstName : string;
  lastName: string;
  userId: string;
  jobTitle: string;
}