package objects;

import Skills.Skill;

public class Hero extends PlayerBall {
    public Skill skill1;
    public Skill skill2;

    public Hero(Area currentArea, String heroName) {
        super(currentArea);  // Вызываем конструктор родительского класса
        this.name = heroName;
    }
    public void setSkill1(Skill skill1) {
        this.skill1 = skill1;
    }

    public void setSkill2(Skill skill2) {
        this.skill2 = skill2;
    }

    public void useSkill1() {
        if (skill1 != null) {
            skill1.useSkill();
        }
    }

    public void useSkill2() {
        if (skill2 != null) {
            skill2.useSkill();
        }
    }

//    @Override
//    public void update(float delta) {
//        super.update(delta); // Обновляем параметры PlayerBall
//
//        // Обновляем навыки (для обработки кулдаунов и прочего)
//        if (skill1 != null) skill1.update(delta);
//        if (skill2 != null) skill2.update(delta);
//    }
}
