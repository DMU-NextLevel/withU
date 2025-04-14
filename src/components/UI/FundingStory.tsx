import React, { JSX } from 'react'
import styled from 'styled-components'

export const FundingStory = (): JSX.Element => {
	return (
		<InfoWrapper>
			<StoryInfo>📢 프로젝트 스토리</StoryInfo>
			<StoryContent />
		</InfoWrapper>
	)
}

export const FundingNews = (): JSX.Element => {
    return (
        <InfoWrapper>
            <StoryInfo>우리 프로젝트는 현재 이렇게 진행중이에요</StoryInfo>
        </InfoWrapper>
    )
}

const InfoWrapper = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
	width: 70%;
`

const StoryInfo = styled.p`
	font-size: 20px;
	font-weight: bold;
	margin-right: auto;
`

const StoryContent = styled.div`
	width: 100%;
	height: 800px;
	background-color: #cdcdcd;
`

