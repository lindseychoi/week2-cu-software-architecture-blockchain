package io.collective.basic;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.collective.basic.Block.calculateHash;

public class Blockchain {
    private List<Block> blockChainList = new ArrayList<Block>();
    public boolean isEmpty() {
        return blockChainList.isEmpty();
    }

    public void add(Block block) {
        blockChainList.add(block);
    }

    public int size() {
        return blockChainList.size();
    }

    public boolean isValid() throws NoSuchAlgorithmException {

            // todo - check an empty chain


            // todo - check a chain of one


            // todo - check a chain of many


        return false;
    }

    /// Supporting functions that you'll need.

    public static Block mine(Block block) throws NoSuchAlgorithmException {
        Block mined = new Block(block.getPreviousHash(), block.getTimestamp(), block.getNonce());

        while (!isMined(mined)) {
            mined = new Block(mined.getPreviousHash(), mined.getTimestamp(), mined.getNonce() + 1);
            String testHash = calculateHash(mined.getPreviousHash() + mined.getTimestamp() + mined.getNonce());
//            System.out.println("test hash" + testHash);
            mined.setHash(testHash);
        }

        return mined;
    }

    public static boolean isMined(Block minedBlock) {
        return minedBlock.getHash().startsWith("00");
    }
}