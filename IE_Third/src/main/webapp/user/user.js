console.log("user.js");
var xhttp = new XMLHttpRequest();

xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {

        var obj = JSON.parse(this.responseText);

        for(var i = 0; i < obj.length; i += 1){
            var htmlSkillOption = obj[i].name;
            var option = document.createElement("OPTION");
            option.setAttribute("value", htmlSkillOption);
            var node = document.createTextNode(htmlSkillOption);
            option.innerHTML = htmlSkillOption;
            var projectElementTag = new DOMParser().parseFromString(htmlSkillOption, 'text/xml');
            console.log(option);
            document.getElementsByClassName("skill-select-box").item(0).appendChild(option);
        }
    }
};
xhttp.open("GET", "../skillList", true);
xhttp.send();

var selectBoxValue = "";
// document.onreadystatechange = function() {
//     if (document.readyState === 'complete') {
//         document.getElementById("skill-select-box-id").onchange = function() {
//             console.log("onchange");
//             selectBoxValue = document.getElementById("skill-select-box-id").value;
//         };
//     }
// };

function selectBoxValueSetter(){
    console.log(selectBoxValue);
    selectBoxValue = document.getElementsByClassName("skill-select-box").item(0).value;
    console.log(selectBoxValue);
}

console.log("user.js");

function addSkillButtonClick(){
    setTimeout(function(){
        var xhttp = new XMLHttpRequest();
        console.log("buttonadd");
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                console.log(this.responseText);
                console.log("add");
                var skillLi = `
                    `+ this.responseText +`: 0
                    <form action="deleteSkill" method="GET">
                        <input type="hidden" name="userSkill" value=""/>
                        <button>Delete</button>
                    </form>
                `;
                var li = document.createElement("LI");
                li.innerHTML = skillLi;
                document.getElementById("skill-unordered-list").appendChild(li);
                // var div = document.createElement("DIV");
                // div.setAttribute("class", "skill-list-item-div");
                // div.innerHTML = `<p class="skill-list-paragraph">`+ 1234 +` </p>
                //  	<span class="label label-default normal" data-hover="--">0</span>`;
                // document.getElementsByClassName("skill-list").item(0).appendChild(div);
            }
            else if(this.readyState == 4 && this.status == 403){
                console.log("forbidden");
            }
        };
        xhttp.open("POST", "../addSkill", true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        if(selectBoxValue !== ""){
            console.log(selectBoxValue);
        }
        xhttp.send("name=" + selectBoxValue);
    }, 300);

}
