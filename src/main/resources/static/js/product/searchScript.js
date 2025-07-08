

productPageMovement = (searchKeyword) => {
    if (searchKeyword) {
        console.log(searchKeyword);
        location.href = `${contextPath}/product?search=${encodeURIComponent(searchKeyword)}`;
        return;
    }
    alert("검색어를 입력해주세요.");
}