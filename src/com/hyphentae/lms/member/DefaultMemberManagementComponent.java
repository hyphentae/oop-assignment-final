package com.hyphentae.lms.member;

import java.sql.SQLException;

public class DefaultMemberManagementComponent implements MemberManagementComponent {

    private final MemberRepository memberRepo;

    public DefaultMemberManagementComponent(MemberRepository memberRepo) {
        this.memberRepo = memberRepo;
    }

    @Override
    public Member getMember(long memberId) throws SQLException {
        Member m = memberRepo.findById(memberId);
        if (m == null) throw new MemberNotFoundException("Member not found");
        return m;
    }
}
