import React, { Component } from 'react';
import axios from 'axios';
export default class UserSkillItem extends React.Component<Props, State>{
    userSkillEndorse: (event: React.MouseEvent<HTMLSpanElement, MouseEvent>) => void = (event) => {
        if(!this.state.isEndorsedNow){

            var data: string = `{
                "skillName":"`+ this.props.skillName+`",
                "userId":"`+ this.props.userId+ `"
            }`;
            var self = this;
            axios.post("http://localhost:8080/endorseCtrl", JSON.parse(data))
            .then(function(resp){
                self.setState({isEndorsedNow: true, skillRate: self.state.skillRate + 1});
                
            });
        }
    };
    constructor(props: Props){
        super(props);
        this.state = {isEndorsedNow: this.props.isEndorsedNow, skillRate: this.props.skillRate};
    }
    render(){
        var endorsedClassName: string = "skill-item";
        var spanHover : any = "";

        if(this.state.isEndorsedNow || this.props.loginUserEndoresed ){
            endorsedClassName += " endorsed";
        }else{
            spanHover = <span className="skill-endorse">+</span>;
        }
        
        return (
            <li className={endorsedClassName}>
            <span className="skill-name">{this.props.skillName}</span>
            <span className="skill-rating" onClick={this.userSkillEndorse}>
                <span>{this.state.skillRate}</span>
                {spanHover}
            </span>
            </li>
        );

    }
}
interface Props{
    skillName: string;
    skillRate: number;
    isEndorsedNow: boolean;
    userId: string;
    loginUserEndoresed: boolean;
}
interface State{
    isEndorsedNow: boolean;
    skillRate : number;

}