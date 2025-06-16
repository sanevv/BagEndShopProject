const startParams = {
    page: 1,
    size: 10
};

const searchTbody = document.getElementById('searchTbody');
function viewMemberList(params){
    axios.get('/api/admin/member', { params: params })
        .then(response => {
            console.log("response 확인 : ", response);

            const memberList = response.data.success.responseData;
            searchTbody.innerHTML = '';
            console.log(memberList);
            if (memberList && memberList.length > 0) {
                memberList.forEach(member => {
                    const tr = document.createElement('tr');
                    tr.className = 'memberInfo';
                    tr.innerHTML = `
                    <td>${member.searchCount}</td>
                    <td>${member.userId}</td>
                    <td>${member.userName}</td>
                    <td>${member.email}</td>
                    <td>${member.gender}</td>
                `;
                    searchTbody.appendChild(tr);
                });
            } else {
                const tr = document.createElement('tr');
                tr.innerHTML = `<td colspan="5">데이터가 존재하지 않습니다.</td>`;
                searchTbody.appendChild(tr);
            }
        })
        .catch(error => {
            console.error(error);
        });
}
viewMemberList(startParams);
const searchTypeSelect = document.getElementById('searchType');
const sizePerPageSelect = document.querySelector('select[name="sizePerPage"]');
const searchValueInput = document.getElementById('searchValue')
sizePerPageSelect.addEventListener('change', function() {
    const size = this.value;
    const params = { size: size };
    viewMemberList(params);
});
searchValueInput.addEventListener("keyup", (event)=>{
    if( event.key === "Enter")
        goSearch();
    if( event.key === "Escape")
        searchValueInput.value = '';
})


function goSearch(){
    const searchType = searchTypeSelect.value;
    if (searchType === ''){
        alert("검색 유형을 선택해주세요.");
        return;
    }
    console.log(searchType);
    const searchValue =  searchValueInput.value.trim();
    if (searchValue === ''){
        alert("검색어를 입력해주세요.");
        return;
    }
    const params = {
        size: sizePerPageSelect.value,
        searchColumn: searchType,
        searchValue: searchValue
    };
    viewMemberList(params);
}