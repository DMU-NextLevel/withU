import React from "react"
import styled from "styled-components"
import FundingInfo from "../components/UI/FundingPage/FundingInfo"
import StarterInfo from '../components/UI/FundingPage/StarterInfo'
import FundingContent from '../components/UI/FundingPage/FundingContent'

const FundingPage: React.FC = () => {
	return (
		<FundingPageWrapper>
			<ColumBox>
				<FundingInfo />
				<StarterInfo />
			</ColumBox>
			<FundingContent></FundingContent>
		</FundingPageWrapper>
	)
}

export default FundingPage

const FundingPageWrapper = styled.div`
	display: flex;
	width: 95%;
	min-height: 60vh;
	padding: 0 3%;
	gap: 2%;
`

const ColumBox = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
	width: 20%;
	gap: 2%;
`