import React from "react";

export function SummaryItem({ productName, count, onQuantityChange }) {
    return (
        <div className="row align-items-center">
            <div className="col-8">
                <h6 className="p-0">
                    {productName}
                    <span className="badge bg-dark text-white ms-2">{count}개</span>
                </h6>
            </div>
            <div className="col-4 text-end">
                <button
                    className="btn btn-sm btn-outline-dark ms-2"
                    onClick={() => onQuantityChange('increment')}
                >
                    ▲
                </button>
                <button
                    className="btn btn-sm btn-outline-dark"
                    onClick={() => onQuantityChange('decrement')}
                    // disabled={count <= 1} // 수량이 1 이하일 때 감소 버튼 비활성화
                >
                    ▼
                </button>

            </div>
        </div>
    );
}
