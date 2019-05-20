import React, { Component } from 'react'
import './../App.css';
export default class ProjectSkillItem extends Component<Props, any> {
    constructor(props: Props){
        super(props);
        this.state = {};
    }
  render() {
    return (
      <div>
        <li className="skill-item">
            <span className="skill-name">{this.props.skillName}</span>
            <span className="skill-rating" >
                <span>{this.props.skillRate}</span>
                {}
            </span>
            </li>
      </div>
    )
  }
}

interface Props{
    skillName: string;
    skillRate: number;
}
