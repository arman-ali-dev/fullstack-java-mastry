# React Testing — Notes

Testing ka matlab hai apne code ke liye alag se code likhna jo automatically check kare ki tumhara actual code sahi kaam kar raha hai ya nahi — bina tumhe manually browser khol ke har cheez baar-baar check karni pade. React apps me testing ke liye 2 main tools mil ke kaam karte hain: **Jest** aur **React Testing Library (RTL)**.

## 1. Jest basics for unit testing

**Jest** ek **testing framework** hai — matlab ye tumhe test likhne aur unhe **run** karne ka poora infrastructure deta hai (test files dhundna, unhe execute karna, result dikhana — pass hue ya fail hue).

**Unit test** ka matlab hai — code ke ek chote, isolated hisse (jaise ek function) ko test karna, ye check karne ke liye ki wo apna kaam sahi se kar raha hai ya nahi.

Basic Jest syntax:

```jsx
function add(a, b) {
  return a + b;
}

test("adds 1 + 2 to equal 3", () => {
  expect(add(1, 2)).toBe(3);
});
```

- `test(...)` — ek test define karta hai. Pehla argument ek **description** hai (batata hai ye test kya check kar raha hai), doosra ek function hai jisme actual testing logic hota hai
- `expect(...)` — jo value tum check karna chahte ho, usko yahan daalte ho
- `.toBe(3)` — ye ek **matcher** hai, batata hai kya expect karte ho. `toBe` exact equality check karta hai. Aur bhi matchers hote hain jaise `.toEqual()` (objects/arrays ke liye), `.toBeTruthy()`, `.toContain()`, wagera

Jab tum `npm test` chalate ho, Jest saari test files (usually `*.test.js` naming wali) dhundta hai, unhe run karta hai, aur batata hai kitne tests **pass** hue aur kitne **fail**:

```
PASS  src/math.test.js
  ✓ adds 1 + 2 to equal 3 (2 ms)

Tests: 1 passed, 1 total
```

Jest ke kuch aur useful features:

**`describe` block** — related tests ko group karne ke liye:

```jsx
describe("math functions", () => {
  test("adds numbers", () => {
    expect(add(1, 2)).toBe(3);
  });

  test("subtracts numbers", () => {
    expect(subtract(5, 2)).toBe(3);
  });
});
```

**Mocking** — jab tumhara code kisi external cheez pe depend karta hai (jaise API call), test ke andar tum **real** API call nahi karna chahte (slow, unreliable, internet chahiye). Isliye us function ko **fake** version se replace kar dete hain (jise **mock** kehte hain), jo predefined data return kare:

```jsx
jest.mock("./userApi");
userApi.getUsers.mockResolvedValue([{ id: 1, name: "Jagir" }]);
```

Isse test fast aur reliable rehta hai, kyunki real network pe depend nahi karta.

## 2. Component testing with React Testing Library

Jest sirf plain JavaScript functions test karne ke liye acha hai, lekin React **components** ko test karna alag cheez hai — component render karna, uske UI ko check karna, usme click/type karke uska behavior test karna. Isi ke liye **React Testing Library (RTL)** use hoti hai — ye Jest ke **saath** kaam karti hai (Jest test run karta hai, RTL components render/interact karne ke tools deta hai).

RTL ki philosophy ye hai: **"test apne app ko waise use karo jaise ek real user karega"** — matlab tum component ke internal implementation (jaise "kaunsa state variable use ho raha hai") test nahi karte, balki jo **user dekhta aur karta hai** (jaise "button pe likha hai 'Submit'", "click karne pe ye text dikhta hai") wahi test karte ho. Isse tests zyada reliable hote hain — agar tum internal code refactor karo (jaise `useState` ko `useReducer` me badlo) bina behavior change kiye, to tests fail nahi honge.

Basic example:

```jsx
import { render, screen } from "@testing-library/react";
import Greeting from "./Greeting";

test("renders greeting message", () => {
  render(<Greeting name="Jagir" />);
  expect(screen.getByText("Hello, Jagir")).toBeInTheDocument();
});
```

- `render(<Greeting name="Jagir" />)` — component ko ek virtual (test ke andar hi, browser nahi) DOM me render karta hai
- `screen` — ek object jisse tum render hue output ko **query** (dhundh) kar sakte ho
- `getByText("Hello, Jagir")` — DOM me wo text dhundhta hai. Agar nahi mila, test fail ho jayega
- `.toBeInTheDocument()` — ye matcher check karta hai ki wo element actually DOM me maujood hai

**User interaction test** karna — jaise button click karna:

```jsx
import { render, screen, fireEvent } from "@testing-library/react";
import Counter from "./Counter";

test("increments count on button click", () => {
  render(<Counter />);
  const button = screen.getByRole("button");

  fireEvent.click(button);

  expect(screen.getByText("Count: 1")).toBeInTheDocument();
});
```

- `getByRole("button")` — element ko uske **accessibility role** se dhundta hai (jaise `button`, `textbox`, `heading`) — ye approach isliye better mana jata hai kyunki ye wahi tarika hai jisse screen readers (visually impaired users ke liye) bhi elements identify karte hain, isliye tumhara test app ko accessible banane me bhi help karta hai
- `fireEvent.click(button)` — button pe click simulate karta hai, bilkul jaise real user click kare
- Click ke baad check karte hain ki UI sahi se update hui ya nahi

Common queries jo RTL deta hai (sab `screen.` ke saath use hote hain):

- `getByText` — text content se element dhundhna
- `getByRole` — accessibility role se (recommended approach)
- `getByLabelText` — form inputs ke liye, unke label se
- `getByPlaceholderText` — input ke placeholder se

**Async testing** — agar component data fetch karta hai (jaise humne API Integration notes me dekha), to test ko wait karna padta hai jab tak data aa na jaye:

```jsx
import { render, screen, waitFor } from "@testing-library/react";

test("displays fetched users", async () => {
  render(<UserList />);

  await waitFor(() => {
    expect(screen.getByText("Jagir")).toBeInTheDocument();
  });
});
```

`waitFor` RTL ko batata hai — "turant check mat karo, thoda wait karo jab tak ye condition sahi na ho jaye (ya timeout ho jaye)" — kyunki API call turant complete nahi hoti, thoda time lagta hai.

Summary: **Jest** test likhne/run karne ka engine hai, **React Testing Library** components ko render karke unse user jaisa interact karne ke tools deta hai. Dono saath milke React components ka reliable, maintainable testing setup banate hain — aur inka focus hamesha "user kya dekhta/karta hai" pe rehta hai, "code ke andar kya ho raha hai" pe nahi.
