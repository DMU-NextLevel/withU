import React, { JSX } from "react"
import styled from "styled-components"
import FundingReward from "./FundingReward"
import FundingFree from "./FundingFree"

const exam = [
    {
        id: 1,
        price: '20000원',
        title: '제목인데요?',
        description: `설명인데요?`,
        date: '2025년 3월 말 제공 예정'
    }
]

const FundingPay = ():JSX.Element => {
    return(
        <FundingPayWrapper>
            <PayStep>
                <Circle>리워드</Circle>
                <Circle>결제</Circle>
            </PayStep>
            <Reward>
                <FundingFree/>
                {exam.map((reward) => (<FundingReward price={reward.price} title={reward.title} description={reward.description} date={reward.date} />))}
            </Reward>
            <PayButton>함께하기</PayButton>
        </FundingPayWrapper>
    )
}

export default FundingPay

const FundingPayWrapper = styled.div`
    display: flex;
    flex-direction: column;
    align-items:center;
    border: none;
    border-radius : 10px;
    height : 80vh;
    background-color:white;
    width: 30vw;
    padding: 30px;
`

const PayStep = styled.div`
    width: 100%;
    display:flex;
    
`

const Reward = styled.div`
    display: column;
    width: 100%;
`

const PayButton = styled.button`
    width: 30%;
    height: 40px;
    border: none;
    background-color: #a66cff;
    color: white;
    font-weight: bold;
    font-size: 18px;
    margin-top: 30px;
    cursor: pointer;
`

const Circle = styled.div`
    width:100px;
    height:100px;
    border-radius:50%;
    background-color: #c9c9c9;
`