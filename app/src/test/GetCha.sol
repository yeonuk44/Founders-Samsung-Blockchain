pragma solidity >=0.4.21 <0.6.0;

contract GetCha {
    struct LegoInfo {
        address payable winner;
        byte32 gameDate;
    }
    
    //돈을 잠시 동안 가지고 있을 주소 
    address payable public legolego;

    //모은 eth의 양 
    uint256 private _bets;

    //게임 시작하면 10명의 주소를 Que구조로 가진다. 
    uint256 private _tail;
    uint256 private _head;
    mapping (uint256 => BetInfo) private _bets;
    
    
    uint256 private _pot;

    //서버에서 받아올 것 
    uint256 internal ethAmount = serverRecive;
    enum BettingResult {Win, Draw}
    event PROVIDED(uint256 index, address indexed bettor, uint256 amount);
    event WIN(uint256 index, address bettor, uint256 amount);

    constructor() public {
        legolego = msg.sender;
    }

    function getBets() public view returns (uint256 bets) {
        return _bets;
    }

    //유저들의 ETH를 받는함수 dlek.
    function provided(address user) public payable returns (bool result) {
        // 이더리움이 정상적으로 왔는지 체크 
        require(msg.value == ethAmount, "Not enough ETH");
        // 주소를 큐에 저장 
        require(pushAddress(user), "Fail to add a new Bet Info");
        emit BET(_tail - 1, msg.sender, msg.value);
        return true;
    }

    //큐에 저장된 주소정보가 서버에서 온 우승자의 주소가 일치하는지 체크
    function checkWinnerAddress(address winnerAddress) public payable returns (bool result) {
        distribute();
        return true;
    }
    
    function distribute() public {
        uint256 cur;
        uint256 transferAmount;
        BetInfo memory b;
        string winnerAddress;
        BettingResult currentBettingResult;

        for(cur=_head;cur<_tail;cur++) {
            b = _bets[cur];
            if(winnerAddress == memory.Checkable) {
                if(currentBettingResult == BettingResult.Win) {
                    transferAmount = transferAfterPayingFee(b.bettor, _bets + ethAmount);
                    _bets = 0;

                    emit WIN(cur, b.bettor, transferAmount);
                }
                
                if(currentBettingResult == BettingResult.Draw) {
                    transferAmount = transferAfterPayingFee(b.bettor, ethAmount);

                    // emit DRAW
                    emit DRAW(cur, b.bettor, transferAmount);
                }
            }
            popAddress(cur);
        }
        _head = cur;
    }

    function paiewdFee(address payable addr, uint256 amount) internal returns (uint256) {
        uint256 fee = 0;
        uint256 amountWithoutFee = amount - fee;
        addr.transfer(amountWithoutFee);
        legolego.transfer(fee);
        return amountWithoutFee;
    }
    


    function popBet(uint256 index) internal returns (bool) {
        delete _bets[index];
        return true;
    }
}