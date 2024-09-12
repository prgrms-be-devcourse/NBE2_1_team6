import React, { useState } from 'react';
import './EditingOrderItems.css'; // CSS 파일 import

function EditingOrderItems({ items, onQuantityChange, onSave, onCancel }) {
    // 내부 상태로 수량 관리
    const [localItems, setLocalItems] = useState(items);

    // 수량 변경 핸들러
    const handleQuantityChange = (itemId, newQuantity) => {
        setLocalItems(prevItems =>
            prevItems.map(item =>
                item.orderItemId === itemId
                    ? { ...item, newQuantity: newQuantity }
                    : item
            )
        );
    };

    // 저장 버튼 클릭 핸들러
    const handleSave = () => {
        const updatedItems = localItems.map(item => ({
            ...item,
            quantity: item.newQuantity || item.quantity  // 새로운 수량을 기존 수량으로 업데이트
        }));
        // onSave 호출 시 변경된 아이템 목록을 전달
        onSave(updatedItems);
    };

    return (
        <div className="order-edit-form">
            <h4>주문 정보</h4>
            <ul>
                {localItems.map(item => (
                    <li key={item.orderItemId}>
                        상품 이름: {item.productName},
                        카테고리: {item.category},
                        현재 수량: {item.quantity}
                        <label>
                            주문 수량 변경:
                            <input
                                type="number"
                                value={item.newQuantity || item.quantity}
                                onChange={(e) => handleQuantityChange(item.orderItemId, parseInt(e.target.value))}
                            />
                        </label>
                    </li>
                ))}
            </ul>
            <div className="button-container">
                <button onClick={handleSave}>저장</button>
                <button onClick={onCancel}>취소</button>
            </div>
        </div>
    );
}

export default EditingOrderItems;
