$(()=>{})
// HIT 상품 게시물을 더보기 위하여 "더보기..." 버튼 클릭액션 이벤트 등록하기
$('button#btnMoreHIT').click(()=>{
    displayHIT(hitPage);
})// HIT 상품 게시물을 더보기 위하여 "더보기..." 버튼 클릭액션 이벤트 등록하기
let hitPage = 1; // HIT상품 게시물 페이지 초기값
// HIT상품 게시물을 더보기 위하여 "더보기..." 버튼 클릭액션에 대한 초기값 호출하기
// 즉, 맨처음에는 "더보기..." 버튼을 클릭하지 않더라도 클릭한 것 처럼 8개의 HIT상품을 게시해주어야 한다는 말이다.
displayHIT(hitPage);
// start가  1 이라면   1~ 8  까지 상품 8개를 보여준다.
// start가  9 이라면   9~ 16  까지 상품 8개를 보여준다.
// start가 17 이라면  17~ 24  까지 상품 8개를 보여준다.
// start가 25 이라면  25~ 32  까지 상품 8개를 보여준다.
// start가 33 이라면  33~ 36  까지 상품 4개를 보여준다.(마지막 상품)


function displayHIT(page) {
    axios.get(`/api/mall/hit?page=${page}`)


    hitPage += 8;

}