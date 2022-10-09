package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class LottoTest {

    @DisplayName("정렬되지 않은 로또 번호로 로또를 생성하면, 정렬된 번호의 로또가 생성되어야 한다.")
    @Test
    void create_withUnsortedNumbers() {
        assertThat(new Lotto(List.of(
                new LottoNumber(3),
                new LottoNumber(2),
                new LottoNumber(1),
                new LottoNumber(4),
                new LottoNumber(5),
                new LottoNumber(6)
        ))).isEqualTo(new Lotto(List.of(
                new LottoNumber(1),
                new LottoNumber(2),
                new LottoNumber(3),
                new LottoNumber(4),
                new LottoNumber(5),
                new LottoNumber(6)
        )));
    }

    @DisplayName("중복된 로또 번호로 로또를 생성하면, 예외가 발생해야 한다.")
    @Test
    void create_givenDuplicatedNumbers() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Lotto(List.of(
                        new LottoNumber(1),
                        new LottoNumber(1),
                        new LottoNumber(3),
                        new LottoNumber(4),
                        new LottoNumber(5),
                        new LottoNumber(6)
                )));
    }

    @DisplayName("충분하지 않은 개수의 로또 번호로 로또를 생성하면, 예외가 발생해야 한다.")
    @Test
    void create_givenNotEnoughSizeOfNumbers() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Lotto(List.of(
                        new LottoNumber(1),
                        new LottoNumber(2),
                        new LottoNumber(3),
                        new LottoNumber(4),
                        new LottoNumber(5)
                )));
    }

    @DisplayName("일치하는 번호의 개수를 반환해야 한다.")
    @ParameterizedTest
    @CsvSource({
            "1,2,3,4,5,6,6",
            "1,2,3,4,5,7,5",
            "1,2,3,4,7,8,4",
            "1,2,3,7,8,9,3",
            "1,2,7,8,9,10,2",
            "1,7,8,9,10,11,1",
            "7,8,9,10,11,12,0"
    })
    void countMatchNumbers(int num1, int num2, int num3, int num4, int num5, int num6, int expected) {
        Lotto lotto = new Lotto(Stream.of(1, 2, 3, 4, 5, 6)
                .map(LottoNumber::new)
                .collect(Collectors.toList()));
        Lotto anotherLotto = new Lotto(Stream.of(num1, num2, num3, num4, num5, num6)
                .map(LottoNumber::new)
                .collect(Collectors.toList()));
        assertThat(lotto.countMatchNumbers(anotherLotto)).isEqualTo(expected);
    }

}