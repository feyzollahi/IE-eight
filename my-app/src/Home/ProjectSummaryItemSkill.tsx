import React, { Component } from 'react'

export default class ProjectSummaryItemSkill extends Component<Props, any> {
    constructor(props: Props){
        super(props);
        this.state = {};
        console.log("skill props " + this.props.name);
    }
  render() {
    return (
      <div style={{display: "flex", margin: "0px 10px", backgroundColor: "#F9F9F9", border: "1px #ECECEC solid"}}>
        <p style={{color: "#757575", marginBottom: "0px", fontSize: "medium"}}>{this.props.name}</p>
      </div>
    )
  }
}
interface Props{
    name: string;
}
