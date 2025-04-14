import React, { JSX, useState } from "react";
import styled from "styled-components";
import UserImage from '../../assets/images/default_profile.png'
import FollowImage from '../../assets/images/Heart.svg'

const StarterInfo = ():JSX.Element => {
    const [isFollowed, setIsFollowed] = useState<boolean>(false)

    const handleFllow = () => {
        setIsFollowed((prev) => !prev)
    }

    return (
        <StarterInfoWrapper>
            <RowBox>
                <StarterProfile src={UserImage} />
                <StarterName>이름입니다</StarterName>
                <FollowStarter onClick={handleFllow} isFllowed={isFollowed}>
                    <img src={FollowImage} alt="❤️" />
                    <p>팔로우</p>
                </FollowStarter>
            </RowBox>
        </StarterInfoWrapper>
    )
}

export default StarterInfo

const StarterInfoWrapper = styled.div`
    display: flex;
    padding: 10px;
    width:95%;
    min-height: 200px;
    border: 3px solid #f3f3f3;
    border-radius: 5px;
`

const StarterProfile = styled.img`
    width:40px;
    height:40px;
    border-radius: 50%;
`

const StarterName = styled.p`
    font-size: 16px;
`

const FollowStarter = styled.button<{isFllowed:boolean}>`
    display: flex;
    flex-direction: row;
    width: 100px;
    height: 40px;
    border: none;
    border-radius: 10px;
    align-items: center;
    justify-content: center;
    gap:10px;
    margin-left: auto;
    img {
        width : 20px;
    }
    &:hover {
        cursor: pointer;
    }
    background-color : ${({isFllowed}) => (isFllowed ? '#f3f3f3' : '#a66cff')}
`

const RowBox = styled.div`
    display: flex;
    width:100%;
    height:30%;
    align-items: center;
`