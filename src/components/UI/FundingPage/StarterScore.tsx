import React, { JSX } from 'react'
import styled from 'styled-components'

interface props {
	title: string
	score: number
}

const StarterScore = ({ title, score }: props): JSX.Element => {
	return (
		<StaterScoreWrapper>
			<ScoreTitle>{title}</ScoreTitle>
			<ScoreGage>
				<ScoreGageFill score={score} />
			</ScoreGage>
		</StaterScoreWrapper>
	)
}

export default StarterScore

const StaterScoreWrapper = styled.div`
	display: flex;
	flex-direction: column;
	width: 60px;
	gap: 5px;
	margin-right: 20px;
`

const ScoreTitle = styled.span`
	font-size: 16px;
`

const ScoreGage = styled.div`
	width: 100%;
	height: 8px;
	background-color: #e1e1e1;
	border-radius: 4px;
	overflow: hidden;
`

const ScoreGageFill = styled.div<{ score: number }>`
	width: ${({ score }) => `${score}%`};
	height: 100%;
	background-color: #a66cff;
	transition: width 0.3s ease;
`
