import React from "react"
import styled from "styled-components"
import FundingInfo from "../components/UI/FundingInfo"

const FundingPage: React.FC = () => {
    return (
        <FundingPageWrapper>
            <FundingInfo/>
            <FundingContent>
            </FundingContent>
        </FundingPageWrapper>
    )
}

export default FundingPage

const FundingPageWrapper = styled.div`
    display: flex;
    width: 100%;
    min-height:60vh;
    padding: 0 3%;
`

const FundingContent = styled.div`
    display: flex;
    width: 80%;
    `