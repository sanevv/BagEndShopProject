if (!userIdParam) {
    alert("사용자 ID가 제공되지 않았습니다.");
    history.back(); // 이전 페이지로 돌아가기
}

function putMemberDetail(responseData){
    const userDetail = responseData.success.responseData;
    const userId = userDetail.userId;
    const userName = userDetail.userName;
    const email = userDetail.email;
    const phoneNumber = userDetail.phoneNumber;
    const zipCode = userDetail.zipCode;
    const address = userDetail.address;
    const gender = userDetail.gender;
    const birthDay = userDetail.birthDay;
    const coin = userDetail.coin;
    const age = userDetail.age;
    const point = userDetail.point;
    const registerAt = userDetail.registerAt;
    document.getElementById('detailTitle').innerText = `::: ${userName}(${userId}) 님의 정보 :::`;
    document.getElementById('userIdInput').innerText = userId;
    document.getElementById('userNameInput').innerText = userName;
    document.getElementById('emailInput').innerText = email;
    document.getElementById('phoneNumberInput').innerText = phoneNumber;
    document.getElementById('zipCodeInput').innerText = zipCode;
    document.getElementById('addressInput').innerText = address;
    document.getElementById('genderInput').innerText = gender;
    document.getElementById('birthInput').innerText = birthDay;
    document.getElementById('ageInput').innerText = age;
    document.getElementById('coinInput').innerText = coin;
    document.getElementById('pointInput').innerText = point;
    document.getElementById('registerAtInput').innerText = registerAt;
}

axios.get(`/api/admin/member/${userIdParam}`)
    .then(response =>{
        console.log(response.data);
        putMemberDetail(response.data);
    })
    .catch(error => {
        console.error("Error fetching member details:", error);
    })
