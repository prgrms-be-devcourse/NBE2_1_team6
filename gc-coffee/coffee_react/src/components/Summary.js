import React from "react";
import { SummaryItem } from "./SummaryItem";

export function Summary({ items = [], onOrderSubmit, onQuantityChange }) {
    const [order, setOrder] = React.useState({
        email: "",
        address: "",
        postcode: ""
    });

    const totalPrice = items.reduce((prev, curr) => prev + (curr.price * curr.count), 0);

    const handleEmailInputChanged = (e) => setOrder({ ...order, email: e.target.value });
    const handleAddressInputChanged = (e) => setOrder({ ...order, address: e.target.value });
    const handlePostcodeInputChanged = (e) => setOrder({ ...order, postcode: e.target.value });

    const handleSubmit = (e) => {
        e.preventDefault();
        if (order.address === "" || order.email === "" || order.postcode === "") {
            alert("입력값을 확인해주세요!");
        } else {
            onOrderSubmit(order);
        }
    };

    // 수량 변경 핸들러
    const handleQuantityChange = (productId, action) => {
        const updatedItems = items.map(item => {
            if (item.productId === productId) {
                const newCount = action === 'increment' ? item.count + 1 : item.count - 1;
                if (newCount <= 0) {
                    // 수량이 0 이하가 되면 아이템을 제거
                    return null;
                }
                return { ...item, count: newCount };
            }
            return item;
        }).filter(item => item !== null); // null인 아이템 필터링

        // 부모 컴포넌트로 변경된 아이템 리스트 전달
        onQuantityChange(updatedItems);
    };

    return (
        <>
            <div>
                <h5 className="m-0 p-0"><b>Summary</b></h5>
            </div>
            <hr />
            {items.length > 0 ? (
                items.map(item => (
                    <SummaryItem
                        key={item.productId}
                        count={item.count}
                        productName={item.productName}
                        onQuantityChange={(action) => handleQuantityChange(item.productId, action)}
                    />
                ))
            ) : (
                <p>상품이 없습니다.</p>
            )}
            <form>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">이메일</label>
                    <input
                        type="email"
                        className="form-control mb-1"
                        value={order.email}
                        onChange={handleEmailInputChanged}
                        id="email"
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="address" className="form-label">주소</label>
                    <input
                        type="text"
                        className="form-control mb-1"
                        value={order.address}
                        onChange={handleAddressInputChanged}
                        id="address"
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="postcode" className="form-label">우편번호</label>
                    <input
                        type="text"
                        className="form-control"
                        value={order.postcode}
                        onChange={handlePostcodeInputChanged}
                        id="postcode"
                    />
                </div>
                <div>당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.</div>
            </form>
            <div className="row pt-2 pb-2 border-top">
                <h5 className="col">총금액</h5>
                <h5 className="col text-end">{totalPrice}원</h5>
            </div>
            <button className="btn btn-dark col-12" onClick={handleSubmit}>결제하기</button>
        </>
    );
}
