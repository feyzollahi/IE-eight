import React, { Component, constructor } from 'react';
import './../App.css';
import Footer from './../Footer';
import Header from './../Header';
// import './bootstrap.css';
import faker from 'faker';
import axios from 'axios'
import { number } from 'prop-types';
import ProfileSkillItem from './ProfileSkillItem';
import { Redirect } from 'react-router';
class Profile extends Component<any, State> {
    static skillItemsJSX: any;

    addSkill = (e: any) => {
        console.log("addSkill");
       var selectBox: any = document.querySelector(".select-skill .skill-select-box");
       var skillName = selectBox.value;
       console.log(selectBox);
       console.log(skillName);
       var data: string = `{
            "name":"`+ skillName+`"
        }`;
        console.log(data);
        var self = this;
        var header:any = {"Authorization": localStorage.getItem("JWT")}

       if(skillName != "def"){
            axios.post("http://localhost:8080/addSkill","",{
                params:{
                    name: skillName
                },
                headers:{
                  header
                }
            })
            .then(function(resp){
              if(resp.status == 200){
                console.log(resp);
                var profileNewSkill: any = <ProfileSkillItem isDeletedNow={false} skillName={skillName} skillRate={0}/>;
                var profileAllSkills: any[] = self.state.skillItemJSX;
                profileAllSkills.push(profileNewSkill);
                self.setState({skillItemJSX: profileAllSkills});
              }
              else if(resp.status == 403){
                self.setState({RedirectToLogin: true});
              }
            });
       }
    }
    componentDidMount(){
      var userData:string;
      var userDataJson:any;
      var self = this;
      var pathName:string = this.props.location.pathname;
      var split = pathName.split("/");
      var userId = split[2];
      if( localStorage.getItem("JWT") == undefined ||  localStorage.getItem("JWT") == null){
        this.setState({RedirectToLogin: true});
        return;
      }
      var header:any = {"Authorization": localStorage.getItem("JWT")}

      axios.get("http://localhost:8080/showSpecifiedUserCtrl",{
          params: {
              userId: "profile"
          },
          headers: {
            header
          }
      })
      .then(function(resp){
          console.log(resp);
          if(resp.status == 200){
              userData = resp.data;
              console.log(userData);
              userDataJson = userData;
              console.log(userData);
              self.setState({firstName: userDataJson.firstName, lastName: userDataJson.lastName
              , bio: userDataJson.bio, jobTitle: userDataJson.jobTitle, id: userDataJson.id
              , uskills: userDataJson.uSkills});
              Profile.skillItemsJSX = [];
              self.state.uskills.forEach(function(uskill){
                  Profile.skillItemsJSX.push(<ProfileSkillItem isDeletedNow={false} skillName={uskill.name} skillRate={uskill.endorsedCount} />);
              });
              self.setState({skillItemJSX: Profile.skillItemsJSX});
              
          }
          else if(resp.status == 403){
            self.setState({RedirectToLogin: true});

          }
      }).catch(error => {
          console.log(error)
      });
      axios.get("http://localhost:8080/skillList",{
        headers:{
          header
        }
      })
      .then(function(resp){
          console.log(resp);
        if(resp.status == 200){
          var skillOptions: any[] = [];
          resp.data.forEach(function(skill: any){
              skillOptions.push(<option value={skill.name}>{skill.name}</option>);
          });
          self.setState({skillOptionsJSX: skillOptions});
        }
        else if(resp.status == 403){
          self.setState({RedirectToLogin: true});
        }
      });
    }
  constructor(props: any){
      super(props);
      this.state = {uskills: [], bio: "", id: "",
       firstName: "", lastName: "",
        jobTitle: "", skillItemJSX: null, skillOptionsJSX: [], RedirectToLogin: false, imageUrlText: ""};
      
  }
  render() {
    
        if(this.state.RedirectToLogin === true){
          return(<Redirect to="/Login"/>);
        }
        
    return (
          
        <div>
        <meta charSet="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta httpEquiv="X-UA-Compatible" content="ie=edge" />
        <link rel="old stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossOrigin="anonymous" />

        <link rel="stylesheet" href="/assets/fonts/flaticons/flaticon.css" />
        <link rel="new stylesheet" href="./user.css" />
        <title>پروفایل محمدرضا کیانی</title>
        <Header/>
        <main>
          <section id="slider">
            <div className="slider-container container">
            </div>
          </section>
          <div id="wrapper" className="container">
            <div className="user-header-wrapper">
              <div className="user-header-container">
                <div className="user-avatar">
                  <img src={faker.image.avatar()} alt="s" />
                </div>
                <div className="user-content">
                  <h3 className="user-name">{this.state.firstName +" " + this.state.lastName}</h3>
                  <span className="user-nickname">{this.state.jobTitle}</span>
                </div>
              </div>
            </div>
            <div className="user-description">
              <p>{this.state.bio}</p>
            </div>
            <div className="select-skills-div">
                <div className="skill-hard-name">
                    <p>مهارت ها:</p>
                </div>
                <div className="select-skill">
                    <select defaultValue="def"  name="skill" className="skill-select-box" placeholder="انتخاب مهارت">
                        <option  value="def" disabled selected>--انتخاب مهارت--</option>
                        {this.state.skillOptionsJSX}
                    </select>
                    <button className="add-skill-button" onClick={e => this.addSkill(e)} >افزودن مهارت</button>
                </div>
            </div>
            <div className="user-skills">
              <ul className="skills-list">
                {this.state.skillItemJSX}
              </ul>
            </div>
          </div>
        </main>
        <Footer/>
      </div>
      
      
      
    );
  }
}
interface State{
    uskills: USkill[];
    id: string;
    jobTitle: string;
    bio: string;
    firstName: string;
    lastName: string;
    skillItemJSX: any;
    skillOptionsJSX: any[];
    imageUrlText: string;
    RedirectToLogin: boolean
}
interface USkill{
    endorsedCount: number;
    name: string;
    endorser: State;
    isLoginUserEndorsed:boolean;
}
export default Profile;
