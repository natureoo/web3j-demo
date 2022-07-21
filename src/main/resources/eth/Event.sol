// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.4.22 <0.9.0;

/** 
 * @dev Implements voting process along with vote delegation
 */
contract Event {
   event DataStored(string data1, string data2);
   string data1;
   string data2;
   function storeData() external {
      emit DataStored("a", "b");
   }
}
