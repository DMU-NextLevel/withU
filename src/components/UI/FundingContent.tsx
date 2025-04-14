import React, { JSX, useState } from 'react'
import styled from 'styled-components'
import { FundingStory } from './FundingStory'

const FundingContent = (): JSX.Element => {
	const [isStoryOpen, setIsStoryOpen] = useState<boolean>(true)
	const [isNewsOpen, setIsNewsOpen] = useState<boolean>(false)
	const [isCommuOpen, setIsCommuOpen] = useState<boolean>(false)

	const handleTab = (label: string) => {
		if (label === '스토리') {
			setIsNewsOpen(false)
			setIsCommuOpen(false)
		} else if (label === '새소식') {
			if (isCommuOpen) {
				setIsNewsOpen(true)
				setIsCommuOpen(false)
			} else {
				setIsNewsOpen(!isNewsOpen)
			}
		} else if (label === '커뮤니티') {
			setIsNewsOpen(true)
			setIsCommuOpen(!isCommuOpen)
		}
	}

	return (
		<FundingContentWrapper>
			<TabBox>
				<TabButton isOpen={true} onClick={() => handleTab('스토리')}>
					스토리
				</TabButton>
				<TabButton isOpen={isNewsOpen} onClick={() => handleTab('새소식')}>
					새 소식
				</TabButton>
				<TabButton isOpen={isCommuOpen} onClick={() => handleTab('커뮤니티')}>
					커뮤니티
				</TabButton>
			</TabBox>
			<MainContent isVisible={isStoryOpen}>
				<FundingStory />
			</MainContent>
		</FundingContentWrapper>
	)
}

export default FundingContent

const FundingContentWrapper = styled.div`
	display: flex;
	width: 80%;
	border: 3px solid #f3f3f3;
	border-radius: 15px;
	flex-direction: column;
`

const MainContent = styled.div<{ isVisible: boolean }>`
	display: flex;
	flex-direction: column;
	align-items: center;
	width: 90%;
	overflow: hidden;
	transition: max-height 0.4s ease;
	padding: ${({ isVisible }) => (isVisible ? '16px' : '0')};
`

const TabBox = styled.div`
	display: flex;
	flex-direction: column;
	align-items: flex-end;
`

const TabButton = styled.div<{ isOpen: boolean }>`
	height: 80px;
	background-color: #a66cff;
	font-size: 20px;
	color: white;
	text-align: center;
	font-weight: bold;
	width: ${({ isOpen }) => (isOpen ? '100%' : '100px')};
	transition: width 0.4s ease;
`
