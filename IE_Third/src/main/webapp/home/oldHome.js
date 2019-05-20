console.log("oldHome.jsp");
var xhttp = new XMLHttpRequest();

xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {

        var obj = JSON.parse(this.responseText);

        for(var i = 0; i < obj.length; i += 1){
            var htmlProject = `
                        <th>`+ obj[i].id +`</th>
                        <th>`+ obj[i].title +`</th>
                        <th>`+ obj[i].budget +`</th>
                        <th>
                            <form action="showSpecifiedProjectCtrl" method="GET">
                                <input type="hidden" name="projectId" value="`+ obj[i].id +`"/>
                                <button>مشاهده</button>
                            </form>
                        </th>
                    `;
            var tr = document.createElement("TR");
            var node = document.createTextNode(htmlProject);
            tr.innerHTML = htmlProject;
            var projectElementTag = new DOMParser().parseFromString(htmlProject, 'text/xml');
            console.log(tr);
            document.getElementById("table").firstElementChild.appendChild(tr);
        }
    }
};
xhttp.open("GET", "projectlist", true);
xhttp.send();